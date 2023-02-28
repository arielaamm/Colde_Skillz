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

    public void calc(Game game, AnalyzeOutput facts) {
        FreePengs freePeng = new FreePengs(game);
        for (Announcement announcement : facts.announcements) {
            if (announcement.getDescription() == "StartSecondPart") {
                decisions.add(new NextPartDecision());

            }
        }
        for (Iceberg iceberg : Knowledge.getClosestIceberg()) {
            if (iceberg.owner != game.getMyself()) {
                boolean b = defend(iceberg, game, freePeng);
                if (b) {
                    game.debug("can defend");
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
                        List<Iceberg> myIces = new ArrayList<>();
                        for (Iceberg iceberg : game.getMyIcebergs()) {
                            myIces.add(iceberg);
                        }
                        int amountAtIceberg = FreePengs.AmountAtIceberg(alert.getTarget(), game);
                        if (amountAtIceberg > 0) {
                            break;
                        } else {
                            amountAtIceberg = -amountAtIceberg;
                        }
                        Vector<Pair<Iceberg, Double>> sortIcebegByDistance = DistanceFunctions.sortIcebegByDistance(
                                alert.getTarget(),
                                myIces);

                        for (Pair<Iceberg, Double> pair : sortIcebegByDistance) {
                            {
                                int free = freePeng.get(pair.getFirst());
                                SendPengDecision sendPengDecision = new SendPengDecision(pair.getFirst(),
                                        alert.getTarget(),
                                        Math.min(amountAtIceberg, free));
                                decisions.add(sendPengDecision);
                                freePeng.update(sendPengDecision);
                                amountAtIceberg -= free;
                                if (amountAtIceberg < 0) {
                                    break;
                                }
                            }
                        }
                    }
                    if (attack.getDescription() == "canOverCome") {
                        CanAttack alert = (CanAttack) attack;
                        int totalSum = 0;
                        int amountAtIceberg = FreePengs.AmountAtIceberg(alert.getTarget(), game);
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
                        int accelerationCost = game.accelerationCost;
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
    }

    @Override
    public String toString() {
        return "Calculator [decisions=" + decisions.toString() + "]\n";
    }
}
