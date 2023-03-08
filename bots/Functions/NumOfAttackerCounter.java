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
     * this func returns for each turn how many attackers will get until this turn
     * and how many defender will be there
     * the first in the pair will be num of attackers and the second num of defender
     *
     * @param target
     * @param game
     * @return {@code Pair} first - num of attackers, second - num of defender
     */
    public static Vector<Pair<Integer, Integer>> getNumberOfAttackers(Iceberg target, Game game, List<Executable> executables) {
        List<PenguinGroup> interestingGroups = new ArrayList<>(); // will be a sub list of all peng groups that will get
        // to the target
        for (PenguinGroup penguinGroup : game.getAllPenguinGroups()) {
            if (penguinGroup.destination == target) {
                interestingGroups.add(penguinGroup);
            }
        }
        List<PenguinGroup> attackers = new ArrayList<>(); // the attackers from the interesting
        List<PenguinGroup> defender = new ArrayList<>(); // the defender from the interesting
        for (PenguinGroup penguinGroup : interestingGroups) {
            if (penguinGroup.owner == target.owner) {
                defender.add(penguinGroup);
            } else {
                attackers.add(penguinGroup);
            }
        }
        int lastAttack = 50;
        Vector<Pair<Integer, Integer>> toRet = new Vector<>();
        if (target.owner == game.getMyself()) {
            toRet.add(new Pair<>(target.penguinAmount, 0));
            for (Executable executable : executables) {
                if (executable instanceof UpgradeIcebergDecision) {
                    toRet.remove(0);
                    toRet.add(0, new Pair<>(target.penguinAmount - target.upgradeCost, 0));
                    // handle upgrade option
                }
                if (executable instanceof SendPengDecision) {
                    SendPengDecision decision = (SendPengDecision) executable;
                    if (target == decision.getSource()) {
                        game.debug("in counter at first turn minus the desicion.getSum");
                        toRet.remove(0);
                        toRet.add(0, new Pair<>(target.penguinAmount - decision.getSum(), 0));
                    }
                    // handle send peng and the source
                }
            }
        } else {
            toRet.add(new Pair<>(0, target.penguinAmount));
        }

        for (int i = 0; i < lastAttack + 2; i++) {

            int first, second;
            Pair<Integer, Integer> lastTurn = toRet.get(i);
            if (lastTurn.getFirst() >= lastTurn.getSecond()) {
                // in this turn th e iceberg is mine
                first = lastTurn.getFirst() + target.penguinsPerTurn;
                for (Executable executable : executables) {
                    if (executable instanceof UpgradeIcebergDecision) {
                        first -= target.upgradeCost;
                    }
                }
                second = lastTurn.getSecond();
            } else {
                first = lastTurn.getFirst();
                second = lastTurn.getSecond() + target.penguinsPerTurn;
                for (Executable executable : executables) {
                    if (executable instanceof UpgradeIcebergDecision) {
                        second -= target.upgradeCost;
                    }
                }
            }
            for (Executable executable : executables) {
                if (executable instanceof SendPengDecision) {
                    SendPengDecision decision = (SendPengDecision) executable;
                    if (target == decision.getTarget()) {
                        second += decision.getSum();
                        // handle when the peng get to the terget
                    }
                }
                if (executable instanceof AccelerateDecision) {
                    AccelerateDecision decision = (AccelerateDecision) executable;
                    int newArriveTurn, lastArriveTime;
                    if (decision.getPenguinGroup() == null) {
                        lastArriveTime = (int) (decision.getSource().getTurnsTillArrival(decision.getTarget())
                                / Math.pow(game.accelerationFactor, decision.getTheNumberTimeOfAcc()));
                        newArriveTurn = (int) (lastArriveTime / game.accelerationFactor) + 1 + decision.getTheNumberTimeOfAcc();
                    } else {
                        lastArriveTime = decision.getPenguinGroup().turnsTillArrival;
                        newArriveTurn = (int) (lastArriveTime / game.accelerationFactor);
                    }
                    if (i + 1 == newArriveTurn) {
                        first += decision.getAmount() / game.accelerationCost;
                    }
                    if (i + 1 == lastArriveTime) {
                        first -= decision.getAmount();
                    }
                    // handle the accelertion decision
                }
            }
            for (PenguinGroup def : defender) {
                if (def.turnsTillArrival == i + 1) {
                    first += def.penguinAmount;
                }
            }
            for (PenguinGroup att : attackers) {
                if (att.turnsTillArrival == i + 1) {
                    second += att.penguinAmount;
                }
            }
            toRet.add(i + 1, new Pair<>(first, second));
            //this ends the game if one of the icepital get occupied
            for (Iceberg myicpital : game.getMyIcepitalIcebergs()) {
                if (target == myicpital) {
                    if (second > first) {
                        return toRet;
                    }
                }
            }
            for (Iceberg enemicpital : game.getEnemyIcepitalIcebergs()) {
                if (target == enemicpital) {
                    if (first > second) {
                        return toRet;
                    }
                }
            }
        }
        // [ ] calc the Long Time Process impact on the number of pengs in the iceberg
        return toRet;
    }

    public static Vector<Pair<Integer, Integer>> getNumberOfAttackers(Iceberg target, Game game, Executable executable) {
        List<Executable> executableList = new ArrayList<>();
        executableList.add(executable);
        return getNumberOfAttackers(target, game, executableList);
    }

    public static Vector<Pair<Integer, Integer>> getNumberOfAttackers(Iceberg target, Game game) {
        return getNumberOfAttackers(target, game, (Executable) null);
    }

    public static Vector<Pair<Integer, Integer>> getNumberOfAttackersNatural(Iceberg target, Game game, List<Executable> executables) {
        boolean ownerIsNat = true;
        Vector<Pair<Integer, Integer>> vec = new Vector<>();
        int natPengs = target.penguinAmount;
        List<PenguinGroup> attackers = new ArrayList<>();
        for (PenguinGroup penguinGroup : game.getAllPenguinGroups()) {
            if (penguinGroup.destination == target) {
                attackers.add(penguinGroup);
            }
        }
        for (int i = 0; i < 50 + 1; i++) {
            int sumOfMy = 0;
            int sumOfHis = 0;
            for (PenguinGroup p : attackers) {
                if (p.turnsTillArrival == i + 1) {
                    if (p.owner == game.getMyself()) {
                        sumOfMy += p.penguinAmount;
                    } else {
                        sumOfHis += p.penguinAmount;
                    }
                }
            }
            if (i != 0) {
                for (Executable executable : executables) {
                    if (executable instanceof SendPengDecision) {
                        SendPengDecision sendPengDecision = (SendPengDecision) executable;
                        if (sendPengDecision.getTarget().getTurnsTillArrival(sendPengDecision.getSource()) == i + 1) {
                            sumOfMy += sendPengDecision.getSum();
                        }
                    }
                }
                if (!ownerIsNat) {
                    Pair<Integer, Integer> lastTurn = vec.get(i - 1);
                    if (lastTurn.getFirst() > lastTurn.getSecond()) {
                        vec.add(new Pair<>(lastTurn.getFirst() + 1 + sumOfMy, lastTurn.getSecond() + sumOfHis));
                    } else {
                        vec.add(new Pair<>(lastTurn.getFirst() + sumOfMy, lastTurn.getSecond() + 1 + sumOfHis));

                    }

                } else {
                    if (sumOfMy > natPengs) {
                        sumOfMy -= natPengs;
                        vec.add(new Pair<>(sumOfMy, 0));
                        ownerIsNat = false;
                    } else if (sumOfHis > natPengs) {
                        sumOfHis -= natPengs;
                        vec.add(new Pair<>(0, sumOfHis));
                        ownerIsNat = false;
                    } else {
                        natPengs -= sumOfMy;
                        natPengs -= sumOfHis;
                        vec.add(new Pair<>(0, natPengs));
                    }
                }
            } else {
                vec.add(new Pair<>(0, natPengs));
            }
        }
        return vec;
    }

    public static Vector<Pair<Integer, Integer>> getNumberOfAttackersNatural(Iceberg target, Game game, Executable executable) {
        List<Executable> executableList = new ArrayList<>();
        executableList.add(executable);
        return getNumberOfAttackersNatural(target, game, executableList);
    }
}