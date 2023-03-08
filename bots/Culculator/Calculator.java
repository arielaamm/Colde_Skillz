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
import bots.Functions.NumOfAttackerCounter;
import bots.LongTimeProcess.LongTimeProcess;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.*;

public class Calculator {
    private List<Executable> decisions;
    Knowledge knowledge = Knowledge.getInstance();

    public Calculator() {
        decisions = new ArrayList<>();
    }

    public List<Executable> getDecisions() {
        return decisions;
    }

    public void calc(Game game, AnalyzeOutput facts) throws CloneNotSupportedException {
        FreePengs freePeng = new FreePengs(game);
        for (Announcement announcement : facts.announcements) {
            if (announcement.getDescription() == "StartSecondPart") {
                decisions.add(new NextPartDecision());

            }
        }
        if (knowledge.isBeltOurs()) {
            for (Iceberg iceberg : Knowledge.getBeltIceberg()) {
                if (iceberg.owner != game.getMyself()) {
                    boolean b = defend(iceberg, game, freePeng);
                    if (b) {
                        game.debug("can defend");
                    }
                }
            }
        }
        int a = 1;
        // switch (knowledge.getPartInGameNumber()) {
        switch (a) { // for now only check part 1
            case 1: // this is part one of the game!!!
                game.debug("start calculating");
                for (Alert alert : facts.alerts) {
                    if (alert.getDescription() == "UnderAttack") {
                        // game.debug("start calcing the under attack alert");
                        // handle all attacks get in
                        UnderAttackAlert underAttack = (UnderAttackAlert) alert;
                        game.debug(underAttack);
                        defend(underAttack.getTarget(), game, freePeng);
                    }
                }

                for (Attack attack : facts.attacks) {
                    if (attack.getDescription() == "CanUpgrade") {
                        game.debug("starts a canUpgrade");
                        if (freePeng.get(((CanUpgrade) attack).getIceberg()) > ((CanUpgrade) attack)
                                .getIceberg().upgradeCost) {
                            game.debug("i have " + freePeng.get(((CanUpgrade) attack).getIceberg()));
                            game.debug("i need " + ((CanUpgrade) attack).getIceberg().upgradeCost);
                            UpgradeIcebergDecision upgradeIcebergDecision = new UpgradeIcebergDecision(
                                    ((CanUpgrade) attack).getIceberg());
                            decisions.add(upgradeIcebergDecision);
                            freePeng.update(upgradeIcebergDecision);
                            game.debug("!!!! Upgraded !!!!");

                        }
                    }
                    // }
                    // for (Attack attack : facts.attacks) {
                    game.debug("reconize an attack alert witch is: " + attack.getDescription());
                    if (attack.getDescription() == "canAttack") {
                        CanAttack alert = (CanAttack) attack;
                        attack(alert.getTarget(), freePeng);
                    }
                    if (attack.getDescription() == "canOverCome") {
                        CanAttack alert = (CanAttack) attack;
                        int totalSum = 0;
                        int amountAtIceberg = freePeng.get(alert.getTarget());
                        List<Iceberg> myIces = new ArrayList<>();
                        for (Iceberg iceberg : game.getMyIcebergs()) {
                            myIces.add(iceberg);
                        }
                        Vector<Pair<Iceberg, Double>> sortIcebegByDistance = DistanceFunctions.sortIcebegByDistance(
                                alert.getTarget(),
                                myIces);

                        for (Pair<Iceberg, Double> pair : DistanceFunctions.sortIcebegByDistance(alert.getTarget(),
                                myIces)) {
                            totalSum += freePeng.get(pair.getFirst());
                        }
                        double accelerationCost = game.accelerationCost;
                        LongTimeProcess longTimeProcessSend = null;
                        LongTimeProcess longTimeProcessAcc = null;
                        for (Pair<Iceberg, Double> pair : sortIcebegByDistance) {
                            SendPengDecision sendPengDecision = new SendPengDecision(pair.getFirst(),
                                    game.getMyIcepitalIcebergs()[0],
                                    freePeng.get(pair.getFirst()));
                            decisions.add(sendPengDecision);
                            freePeng.update(sendPengDecision);
                        }
                        int lastTurn = sortIcebegByDistance.lastElement().getFirst()
                                .getTurnsTillArrival(game.getMyIcepitalIcebergs()[0]);
                        longTimeProcessSend = new LongTimeProcess(lastTurn + 1,
                                new SendPengDecision(game.getMyIcepitalIcebergs()[0], alert.getTarget(), totalSum),
                                "SendPengDecision");
                        if (totalSum / (accelerationCost * accelerationCost) > amountAtIceberg) {
                            longTimeProcessAcc = new LongTimeProcess(lastTurn + 2,
                                    new AccelerateDecision(game.getMyIcepitalIcebergs()[0], alert.getTarget(), totalSum,
                                            2),
                                    "AccelerateDecision");
                        } else if (totalSum / (accelerationCost) > amountAtIceberg) {
                            longTimeProcessAcc = new LongTimeProcess(lastTurn + 2,
                                    new AccelerateDecision(game.getMyIcepitalIcebergs()[0], alert.getTarget(), totalSum,
                                            1),
                                    "AccelerateDecision");
                        } else {
                            longTimeProcessAcc = new LongTimeProcess(lastTurn + 2,
                                    new AccelerateDecision(game.getMyIcepitalIcebergs()[0], alert.getTarget(), totalSum,
                                            0),
                                    "AccelerateDecision");
                        }
                        knowledge.addProcess(longTimeProcessSend);
                        knowledge.addProcess(longTimeProcessAcc);
                        // int free = freePeng.get(pair.getFirst());
                        // SendPengDecision sendPengDecision = new SendPengDecision(pair.getFirst(),
                        // alert.getTarget(),
                        // Math.min(amountAtIceberg, free));
                        // decisions.add(sendPengDecision);
                        // freePeng.update(sendPengDecision);
                        // amountAtIceberg -= free;
                        // if (amountAtIceberg < 0) {
                        // break;
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
    private boolean defend(Iceberg underAttack, Game game, FreePengs freePeng) {
        if (freePeng.ownerAtTheEnd(underAttack) != game.getMyself()) {
            game.debug("defend a mark");
            List<Iceberg> underAttackIcebergs = new ArrayList<>();
            underAttackIcebergs.add(underAttack);
            List<Iceberg> myIcebergs = new ArrayList<>();
            for (Iceberg iceberg : game.getMyIcebergs()) {
                myIcebergs.add(iceberg);
            }
            // building the to the function that sort all the icebergs
            Vector<Pair<Iceberg, Double>> closestToTarget = DistanceFunctions.sortIcebegByDistance(underAttackIcebergs,
                    myIcebergs);
            int firstTurnLose = 0;
            int missingPengs = 0;
            try {
                missingPengs = -freePeng.get(underAttack);
            } catch (Exception e) {
                missingPengs = 0;
            }
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
            // now make faster the peng allready in way but get late
            // fing all of those peng groups
            for (PenguinGroup penguinGroup : game.getMyPenguinGroups()) {
                if (penguinGroup.destination == underAttack) {
                    if (penguinGroup.turnsTillArrival < firstTurnLose) {
                        if (game.accelerationCost != 0) {
                            AccelerateDecision accelerateDecision = new AccelerateDecision(penguinGroup);
                            knowledge.addProcess(new LongTimeProcess(1, accelerateDecision, "accelerateDecision"));
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
                // few lines before, better to fast late peng than send new
            }
            return missingPengs == 0;
        } else {
            return true;
        }
    }

//[ ]: see this func if you think it will work, doing recursive call to check the "tree options"
    private boolean attack(Iceberg target, FreePengs freePengs) throws CloneNotSupportedException {
        if (freePengs.ownerAtTheEnd(target) != Knowledge.getGame().getMyself()) {
            if (Knowledge.canAcclerate()) {
                //prefer to acc, but if will see that the acc מיותר will throw it
                for (PenguinGroup penguinGroup : Knowledge.getGame().getMyPenguinGroups()) {
                    if (penguinGroup.destination == target) {
                        Executable executable = new AccelerateDecision(penguinGroup);
                        FreePengs goodFreePeng = attackHelper(target, freePengs.explore(executable));
                        //now check if can left the acc and win without the acc
                        List<Executable> executableList = goodFreePeng.getWayFromBase();
                        FreePengs withoutFirstAcc = (FreePengs) freePengs.clone();
                        for (int i = 0; i < executableList.size(); i++) {
                            if (i == 0) {
                                continue;
                            }
                            withoutFirstAcc.update(executableList.get(i));
                        }
                        if (goodFreePeng.ownerAtTheEnd(target) != Knowledge.getGame().getMyself()) {
                            return false;
                        }
                        if (withoutFirstAcc.ownerAtTheEnd(target) == Knowledge.getGame().getMyself()) {
                            //dont necessary to acc
                            for (Executable executable1 : withoutFirstAcc.getWayFromBase()) {
                                decisions.add(executable1);
                                freePengs.update(executable1);
                            }
                        } else {
                            //need the acc
                            for (Executable executable1 : goodFreePeng.getWayFromBase()) {
                                decisions.add(executable1);
                                freePengs.update(executable1);
                            }
                        }
                        return true;

                    }
                }
            }
            Iceberg sendHelp = FindClosestSendHelp(target, freePengs);
            if (sendHelp == null) {
                return false;
            }
            FreePengs goodFreePeng = culcNewFreePeng(freePengs, target, sendHelp);
            if (goodFreePeng == null) {
                return false;
            }
            Knowledge.getGame().debug("in the end the owner is: " + goodFreePeng.ownerAtTheEnd(target));
            if (goodFreePeng.ownerAtTheEnd(target) == Knowledge.getGame().getMyself()) {
                for (Executable executable1 : goodFreePeng.getWayFromBase()) {
                    decisions.add(executable1);
                    freePengs.update(executable1);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    private FreePengs attackHelper(Iceberg target, FreePengs freePengs) throws CloneNotSupportedException {
        Knowledge.getGame().debug("in attack helper");
        Knowledge.getGame().debug(freePengs.getMap());
        Knowledge.getGame().debug(freePengs.getNaturalMap());
        Knowledge.getGame().debug("the owner in the end of target is = " + freePengs.ownerAtTheEnd(target));
        if (freePengs.ownerAtTheEnd(target) != Knowledge.getGame().getMyself()) {
            if (Knowledge.canAcclerate()) {
                //prefer to acc, but if will see that the acc מיותר will throw it
                for (PenguinGroup penguinGroup : Knowledge.getGame().getMyPenguinGroups()) {
                    boolean allreadyAcc = false;
                    for (Executable executable : freePengs.getWayFromBase()){
                        if (executable instanceof AccelerateDecision) {
                            if ( ((AccelerateDecision) executable).getPenguinGroup() == penguinGroup) {
                                //not acc same group twice
                                allreadyAcc = true;
                            }
                        }
                    }
                    if (allreadyAcc) {
                        continue;
                    }
                    if (penguinGroup.destination == target) {
                        Executable executable = new AccelerateDecision(penguinGroup);
                        FreePengs goodFreePeng = attackHelper(target, freePengs.explore(executable));
                        //now check if can left the acc and win without the acc
                        List<Executable> executableList = goodFreePeng.getWayFromBase();
                        FreePengs withoutFirstAcc = (FreePengs) freePengs.clone();
                        for (int i = 0; i < executableList.size(); i++) {
                            if (i == 0) {
                                continue;
                            }
                            withoutFirstAcc.update(executableList.get(i));
                        }
                        if (goodFreePeng.ownerAtTheEnd(target) != Knowledge.getGame().getMyself()) {
                            return freePengs;
                        }
                        if (withoutFirstAcc.ownerAtTheEnd(target) == Knowledge.getGame().getMyself()) {
                            //dont necessary to acc
                            return withoutFirstAcc;
                        } else {
                            //need the acc
                            return goodFreePeng;
                        }
                    }
                }
            }
            Iceberg sendHelp = FindClosestSendHelp(target, freePengs);
            if (sendHelp == null) {
                Knowledge.getGame().debug("sendHelp s null");
                return null;
            }
            FreePengs goodFreePeng = culcNewFreePeng(freePengs, target, sendHelp);
            if (goodFreePeng == null) {
                return freePengs;
            }
            if (goodFreePeng.ownerAtTheEnd(target) == Knowledge.getGame().getMyself()) {
                return goodFreePeng;
            } else {
                return null;
            }


        } else {
            Knowledge.getGame().debug("i am winning");
            return freePengs;
        }
    }

    private FreePengs culcNewFreePeng(FreePengs freePengs, Iceberg target, Iceberg sendHelp) throws CloneNotSupportedException {
        Knowledge.getGame().debug("culcNewFreePeng" );
        int turnArrive = sendHelp.getTurnsTillArrival(target);
        Pair<Integer, Integer> turnArrivePair;
        if (target.owner == Knowledge.getGame().getNeutral()) {
            turnArrivePair = freePengs.getNaturalMap().get(target).get(turnArrive);
        } else {
            turnArrivePair = freePengs.getMap().get(target).get(turnArrive);
        }
        int sumOfPengInTeagetWhenArrive = - turnArrivePair.getFirst() + turnArrivePair.getSecond();
        int numOfAttackers = Math.min(sumOfPengInTeagetWhenArrive + 1, freePengs.get(sendHelp));
        Executable executable = new SendPengDecision(sendHelp, target, numOfAttackers);
        Knowledge.getGame().debug("the decision is " + executable);
        FreePengs afterChange = freePengs.explore(executable);
        FreePengs goodFreePeng = attackHelper(target, afterChange);
        return goodFreePeng;
    }

    private Iceberg FindClosestSendHelp(Iceberg target, FreePengs freePengs) {
        List<Pair<Iceberg, Double>> closests = DistanceFunctions.sortIcebegByDistance(target, Arrays.asList(Knowledge.getGame().getMyIcebergs()));
        Iceberg sendHelp = null;
        for (Pair<Iceberg, Double> iceberg  : closests) {
            if(freePengs.get(iceberg.getFirst()) > 0) {
                sendHelp = iceberg.getFirst();
                break;
            } else {
                Knowledge.getGame().debug("dont chose to send from " + iceberg.getFirst() + "because hase only " + freePengs.get(iceberg.getFirst()));
            }
        }
        return sendHelp;
    }


    @Override
    public String toString() {
        return "Calculator [decisions=" + decisions.toString() + "]\n";
    }
}
