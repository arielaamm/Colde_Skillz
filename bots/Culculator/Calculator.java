package bots.Culculator;

import bots.DataBases.Knowledge;
import bots.Exeuter.Executable;
import bots.Exeuter.SendPeng;
import bots.Exeuter.UpgradeIceberg;
import bots.Facts.Alert;
import bots.Facts.Alerts.UnderAttackAlert;
import bots.Facts.AnalyzeOutput;
import bots.Facts.Attack;
import bots.Facts.Attacks.CanAttack;
import bots.Facts.Attacks.CanUpgrade;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {
    private List<Executable> decisions;

    public Calculator() {
        decisions = new ArrayList<>();
    }

    public List<Executable> getDecisions() {
        return decisions;
    }

    public void calc(Game game, AnalyzeOutput facts) {
        switch (Knowledge.getInstance().getPartInGameNumber()) {
            case 1: //this is part one of the game!!!
                Map<Iceberg, Integer> freePeng = new HashMap<>(); //here keep how many free pengs i have
                for (Iceberg iceberg : game.getMyIcebergs()) {
                    //put the many of pengs in the iceberg in the start of the freePeng
                    freePeng.put(iceberg, iceberg.penguinAmount);
                }
                for (Alert alert : facts.alerts) {
                    if (alert.getDescription() == "UnderAttack") {
                        //handle all attacks get in
                        UnderAttackAlert underAttack = (UnderAttackAlert) alert;
                        int sumOfAttackers = 0; //the sum of the attackers;
                        int closet = 100; //turns until the first group get
                        for (PenguinGroup group : underAttack.getAttackers()) {
                            sumOfAttackers += group.penguinAmount;
                            if (group.turnsTillArrival < closet) {
                                closet = group.turnsTillArrival;
                            }
                        }
                        sumOfAttackers -= closet * underAttack.getTarget().penguinsPerTurn;
                        if (sumOfAttackers < 0) {
                            sumOfAttackers = 0;
                        }
                        freePeng.put(underAttack.getTarget(), freePeng.get(underAttack.getTarget()) - sumOfAttackers);
                        for (Iceberg iceberg : game.getMyIcebergs()) {
                            if (freePeng.get(iceberg) < 0) {
                                defend(iceberg, game, freePeng, underAttack);
                            }
                        }
                    }
                }
                for (Attack attack : facts.attacks) {
                    if (attack.getDescription() == "CanAttack") {
                        CanAttack alert = (CanAttack) attack;
                        //find closest iceberg to attack from. NOT DONE
                        for (Iceberg iceberg : game.getMyIcebergs()) {
                            if (freePeng.get(iceberg) > alert.getTarget().penguinAmount) {
                                //attack from this iceberg
                                SendPeng sendPeng = new SendPeng(iceberg, alert.getTarget(), alert.getTarget().penguinAmount + 1);
                                decisions.add(sendPeng);
                                freePeng.put(iceberg, freePeng.get(iceberg) - alert.getTarget().penguinAmount + 1);
                            }
                        }
                    }
                }
                for (Attack attack : facts.attacks) {
                    if (attack.getDescription() == "CanUpgrade") {
                        UpgradeIceberg upgradeIceberg = new UpgradeIceberg(((CanUpgrade) attack).getToUpgrade());
                        decisions.add(upgradeIceberg);
                    }
                }
                break;
            case 2: //this is part two of the game!!!

                break;
            case 3: //this is part three of the game!!!
                break;

        }
    return;
    }

    /**
     * this func send pengs from closest iceberg to the attacked one if it wll helps
     * @param underAttack
     * @param game
     * @param freePeng
     * @param alert
     * @return will be safe
     */
    private boolean defend(Iceberg underAttack, Game game, Map<Iceberg, Integer> freePeng, UnderAttackAlert alert) {
        int allreadyOnWay = 0;
        while (freePeng.get(underAttack) < 0) {
            //lose the point
            //want find the closest myIceberg and send peng to help
            Iceberg closest = game.getMyIcebergs()[0];
            for (Iceberg mine : game.getMyIcebergs()) {
                if (underAttack.getTurnsTillArrival(closest) > underAttack.getTurnsTillArrival(mine)) {
                    closest = mine;
                }
            }
            //counts the number of attackers and calc the farest enemy
            PenguinGroup farest = alert.getAttackers().get(0);
            int sumOfAttackers = 0;
            for (PenguinGroup attacker : alert.getAttackers()) {
                sumOfAttackers += attacker.penguinAmount;
                if (farest.turnsTillArrival < attacker.turnsTillArrival)
                    farest = attacker;
            }
            //culc the number of missing pengs to survive
            int diifToWin = -allreadyOnWay - underAttack.penguinAmount - underAttack.penguinsPerTurn * farest.turnsTillArrival + sumOfAttackers;

                //count the number of enemies that the new peng will fight
            int sumOfLateAttackers = 0;
            for (PenguinGroup attacker : alert.getAttackers()) {
                if (attacker.turnsTillArrival > closest.getTurnsTillArrival(underAttack)) {
                    sumOfLateAttackers += attacker.penguinAmount;
                }
            }
            boolean canStandUntilCame = false;
            //check if the ice will survive until the help came
            if (sumOfAttackers - sumOfLateAttackers <  underAttack.penguinAmount + underAttack.penguinsPerTurn * closest.getTurnsTillArrival(underAttack)) {
                // will survive
                canStandUntilCame = true;
            }
            if (!canStandUntilCame) {
                //bye bye Iceberg
                return false;
            }
            //fight till die!!!!
            //now send the minimum between freePeng and how need to defend in the closest to
            if (freePeng.get(closest) > diifToWin) {
                // can only send from here
                SendPeng sendPeng = new SendPeng(closest, underAttack, diifToWin);
                decisions.add(sendPeng);
                //Update the freePeng
                freePeng.put(closest, freePeng.get(closest) - diifToWin + 1);
                freePeng.put(underAttack, 1);
                return true;
            }
            return false;
        }
    return false;
    }
}
