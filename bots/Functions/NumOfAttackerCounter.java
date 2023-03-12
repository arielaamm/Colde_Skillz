package bots.Functions;

import bots.DataBases.Pair;
import bots.Executer.AccelerateDecision;
import bots.Executer.Executable;
import bots.Executer.SendPengDecision;
import bots.Executer.UpgradeIcebergDecision;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NumOfAttackerCounter {
    /**
     * this func returns for each turn how many attackers will get until this turn and how many defender will be there
     * the first in the pair will be num of attackers and the second num of defender
     * @param target
     * @param game
     * @return {@code Pair} first - num of attackers,  second - num of defender
     */
    public static Vector<Pair<Integer, Integer>> getNumberOfAttackers(Iceberg target, Game game, Executable executable) {
        if (target == null) {
            return null;
        }
        List<PenguinGroup> interestingGroups = new ArrayList<>(); //will be a sub list of all peng groups that will get to the target
        for (PenguinGroup penguinGroup : game.getAllPenguinGroups()) {
            if (penguinGroup.destination == target) {
                interestingGroups.add(penguinGroup);
            }
        }
        int lastAttack = 0;
        List<PenguinGroup> attackers = new ArrayList<>(); //the attackers from the interesting
        List<PenguinGroup> defender = new ArrayList<>(); //the defender from the interesting
        for (PenguinGroup penguinGroup : interestingGroups) {
            if (penguinGroup.turnsTillArrival > lastAttack) {
                lastAttack = penguinGroup.turnsTillArrival;
            }
            if (penguinGroup.owner == target.owner) {
                defender.add(penguinGroup);
            } else {
                attackers.add(penguinGroup);
            }
        }
        Vector<Pair<Integer, Integer>> toRet = new Vector<>();
        if (target.owner == game.getMyself()){
            toRet.add(new Pair<>(target.penguinAmount, 0));
            if (executable instanceof UpgradeIcebergDecision){
                toRet.add(0, new Pair<>(target.penguinAmount - target.upgradeCost, 0));
                //handle upgrade option
            }
            if (executable instanceof SendPengDecision) {
                SendPengDecision decision = (SendPengDecision) executable;
                if (target == decision.getSource()) {
                    toRet.add(0, new Pair<>(target.penguinAmount - decision.getSum(), 0));
                }
                //handle send peng and the source
            }
        } else {
            toRet.add(new Pair<>(0, target.penguinAmount));
        }
        lastAttack = Math.max(lastAttack, 40);
        for (int i = 0; i < lastAttack ; i++) {
            int first, second;
            Pair<Integer, Integer> lastTurn = toRet.get(i);
            if (lastTurn.getFirst() > lastTurn.getSecond()){
                //in this turn th e iceberg is mine
                first = lastTurn.getFirst() + target.penguinsPerTurn;
                if (executable instanceof UpgradeIcebergDecision) {
                    first += 1;
                }
                second = lastTurn.getSecond();
            } else {
                first = lastTurn.getFirst();
                second = lastTurn.getSecond() + target.penguinsPerTurn;
                if (executable instanceof UpgradeIcebergDecision) {
                    second += 1;
                }
            }
            if (executable instanceof SendPengDecision ) {
                SendPengDecision decision = (SendPengDecision) executable;
                if (target == decision.getTarget()) {
                    second += decision.getSum();
                    //handle when the peng get to the terget
                }
            }
            if (executable instanceof AccelerateDecision) {
                AccelerateDecision decision = (AccelerateDecision) executable;
                int newArriveTurn, lastArriveTime;
                if (decision.getPenguinGroup() == null) {
                    lastArriveTime = (int) (decision.getSource().getTurnsTillArrival(decision.getTarget()) / Math.pow(game.accelerationFactor, decision.getTheNumberTimeOfAcc()));
                    newArriveTurn = (int) (lastArriveTime / game.accelerationFactor) + 1 + decision.getTheNumberTimeOfAcc();
                } else {
                    lastArriveTime = decision.getPenguinGroup().turnsTillArrival;
                    newArriveTurn = (int) (lastArriveTime / game.accelerationFactor);
                }
                if (i+1 == newArriveTurn) {
                    first += decision.getAmount() / game.accelerationCost;
                }
                if (i+1 == lastArriveTime) {
                    first -= decision.getAmount();
                }
                //handle the accelertion decision
            }
            for (PenguinGroup def : defender) {
                if (def.turnsTillArrival == i + 1) {
                    first += def.penguinAmount;
                }
            }
            for (PenguinGroup att : attackers) {
                if (att.turnsTillArrival== i + 1){
                    second += att.penguinAmount;
                }
            }
            toRet.add(new Pair<>(first, second));
        }
        //[ ] calc the Long Time Process impact on the number of pengs in the iceberg
        return toRet;
    }

    public static Vector<Pair<Integer, Integer>> getNumberOfAttackers(Iceberg target, Game game) {
        return getNumberOfAttackers(target, game, null);
    }

    }
