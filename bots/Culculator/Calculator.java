package bots.Culculator;

import bots.DataBases.FreePengs;
import bots.DataBases.Knowledge;
import bots.DataBases.Pair;
import bots.Executer.*;
import bots.Facts.Alert;
import bots.Facts.Alerts.UnderAttackAlert;
import bots.Facts.AnalyzeOutput;
import bots.Facts.Announcement;
import bots.Facts.Attack;
import bots.Facts.Attacks.CanAttack;
import bots.Facts.Attacks.CanUpgrade;
import bots.Functions.DistanceFunctions;
import bots.Functions.IsAttack;
import bots.Functions.NumOfAttackerCounter;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.*;

public class Calculator {
    private List<Executable> decisions;

    public Calculator() {
        decisions = new ArrayList<>();
    }

    public List<Executable> getDecisions() {
        return decisions;
    }

    public void calc(Game game, AnalyzeOutput facts) {
        for (Announcement announcement : facts.announcements) {
            if (announcement.getDescription() == "StartSecondPart") {
                decisions.add(new NextPartDecision());
            }
        }
        Knowledge knowledge = Knowledge.getInstance();
        int a = 1;
        // switch (knowledge.getPartInGameNumber()) {
        switch (a) { // for now only check part 1
            case 1: // this is part one of the game!!!
                game.debug("start calculating");
                FreePengs freePeng = new FreePengs(game);
                for (Alert alert : facts.alerts) {
                    if (alert.getDescription() == "UnderAttack") {
                        // game.debug("start calcing the under attack alert");
                        // handle all attacks get in
                        UnderAttackAlert underAttack = (UnderAttackAlert) alert;
                        game.debug(underAttack);
                        defend(underAttack.getTarget(), game, freePeng, underAttack);
                    }
                }
                
                for (Attack attack : facts.attacks) {
                    if (attack.getDescription() == "CanUpgrade") {
                        game.debug("starts a canUpgrade");
                        if (freePeng.get(((CanUpgrade) attack).getToUpgrade()) > ((CanUpgrade) attack)
                                .getToUpgrade().upgradeCost) {
                            game.debug("i have " + freePeng.get(((CanUpgrade) attack).getToUpgrade()));
                            game.debug("i need " + ((CanUpgrade) attack).getToUpgrade().upgradeCost);
                            UpgradeIcebergDecision upgradeIcebergDecision = new UpgradeIcebergDecision(
                                    ((CanUpgrade) attack).getToUpgrade());
                            decisions.add(upgradeIcebergDecision);
                            freePeng.update(upgradeIcebergDecision);
                            game.debug("!!!! Upgraded !!!!");

                        }
                    }
                }
                for (Attack attack : facts.attacks) {
                    game.debug("reconize an attack alert witch is: " + attack.getDescription());
                    if (attack.getDescription() == "canAttack") {
                        CanAttack alert = (CanAttack) attack;
                        // find closest iceberg to attack from. NOT DONE
                        List<Iceberg> myIces = new ArrayList<>();
                        for (Iceberg iceberg : game.getMyIcebergs()) {
                            myIces.add(iceberg);
                        }
                        List<Iceberg> target = new ArrayList<>();
                        target.add(alert.getTarget());
                        for (Pair<Iceberg, Double> temp : DistanceFunctions.sortIcebegByDistance(target, myIces)) {
                            Iceberg iceberg = temp.getFirst();
                            int spierPengs = 0;
                            if (iceberg.owner == game.getEnemy()) {
                                spierPengs = iceberg.penguinsPerTurn * (1 + temp.getSecond().intValue());
                            }
                            if (freePeng.get(iceberg) > alert.getTarget().penguinAmount + spierPengs) {
                                if (!(IsAttack.isEnemyAttacks(iceberg, game) && iceberg.owner == game.getNeutral())) {
                                    // attack from this iceberg
                                    SendPengDecision sendPengDecision = new SendPengDecision(iceberg, alert.getTarget(),
                                            alert.getTarget().penguinAmount + 1 + spierPengs);
                                    game.debug("decide to attack from " + iceberg.id + " with "
                                            + alert.getTarget().penguinAmount + 1 + " pengs");
                                    decisions.add(sendPengDecision);
                                    freePeng.update(sendPengDecision);
                                } else {
                                    PenguinGroup theAttacker = new PenguinGroup();
                                    for (PenguinGroup penguinGroup : game.getEnemyPenguinGroups()) {
                                        if (penguinGroup.destination == alert.getTarget()) {
                                            theAttacker = penguinGroup;
                                            break;
                                        }
                                    }
                                    if (theAttacker.turnsTillArrival < iceberg.getTurnsTillArrival(alert.getTarget())) {
                                        // attack from this iceberg
                                        SendPengDecision sendPengDecision = new SendPengDecision(iceberg,
                                                alert.getTarget(),
                                                alert.getTarget().penguinAmount + 1 + spierPengs);
                                        game.debug("decide to attack from " + iceberg.id + " with " + 
                                                    (alert.getTarget().penguinAmount + 1) + " pengs");
                                        decisions.add(sendPengDecision);
                                        freePeng.update(sendPengDecision);
                                    } else {
                                        game.debug("deside to not atack from " + iceberg.id);
                                        game.debug("enemy i didnt attack is: " + alert.getTarget().id);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 2: // this is part two of the game!!! for now same as one;

                break;
            case 3: // this is part three of the game!!!
                break;

        }

        return;
    }

    /**
     * this func send pengs from closest iceberg to the attacked one if it wll helps
     * 
     * @param underAttack
     * @param game
     * @param freePeng
     * @param alert
     * @return {@code true} - win, {@code false} - lose
     */
    private boolean defend(Iceberg underAttack, Game game, FreePengs freePeng, UnderAttackAlert alert) {
        game.debug("defend a mark");
        List<Iceberg> underAttackIcebergs = new ArrayList<>();
        underAttackIcebergs.add(underAttack);
        List<Iceberg>  myIcebergs = new ArrayList<>();
        for (Iceberg iceberg : game.getMyIcebergs()) {
            myIcebergs.add(iceberg);
        }
        // building the to the function that sort all the icebergs
        Vector<Pair<Iceberg, Double>> closestToTarget = DistanceFunctions.sortIcebegByDistance(underAttackIcebergs,
                myIcebergs);
        int firstTurnLose = 0;
        int missingPengs = -freePeng.get(underAttack);
        Vector<Pair<Integer, Integer>> nextTurns = NumOfAttackerCounter.getNumberOfAttackers(underAttack, game);
        for (int i = 0; i < nextTurns.size(); i++) {
            if (nextTurns.get(i).getFirst() - nextTurns.get(i).getSecond() < 0) {
                firstTurnLose = i;
                break;
            }
        }
        if (firstTurnLose == 0) {
            return false;
        }
        //now make faster the peng allready in way but get late
        //fing all of those peng groups
        for (PenguinGroup penguinGroup : game.getMyPenguinGroups()) {
            if (penguinGroup.destination == underAttack) {
                if (penguinGroup.turnsTillArrival < firstTurnLose) {
                    if (game.accelerationCost != 0) {
                        AccelerateDecision accelerateDecision = new AccelerateDecision(penguinGroup);
                        decisions.add(accelerateDecision);
                        missingPengs -= penguinGroup.penguinAmount / game.accelerationCost;
                    }
                }
            }
        }
        // now send help
        game.debug("missing" + missingPengs + "peng to survive");
        for (Pair<Iceberg, Double> canSendHelp : closestToTarget) {
            int freeHere = freePeng.get(canSendHelp.getFirst());
            if (freeHere <= 0) {
                continue;
            }
            if (canSendHelp.getFirst().getTurnsTillArrival(underAttack) < firstTurnLose) {
                game.debug("find source to send");
                int numSent = Math.min(missingPengs, freeHere);
                missingPengs -= numSent;
                SendPengDecision sendPengDecision = new SendPengDecision(canSendHelp.getFirst(), underAttack, numSent);
                decisions.add(sendPengDecision);
                freePeng.update(sendPengDecision);
            }
            // [x] have to add option to make the peng faster and then they will help
            //few lines before, better to fast late peng than send new
        }
        return missingPengs == 0;
    }

    @Override
    public String toString() {
        return "Calculator [decisions=" + decisions.toString() + "]\n";
    }
}
