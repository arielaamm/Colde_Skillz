package bots.Analyze.Attack;

import bots.DataBases.Knowledge;
import bots.DataBases.Pair;
import bots.Facts.Attack;
import bots.Facts.Attacks.CanAttack;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;
import java.util.*;

//NOTE: change the name from CanAttack -> CanAttackAnalyze becaues duplication
public class CanAttackAnalyze extends AttackOption {
    AttackOption attackOption;

    public CanAttackAnalyze(AttackOption attackOption) {
        this.attackOption = attackOption;
    }

    @Override
    public List<Attack> getAlerts(Game game) {
        List<Attack> attackList = attackOption.getAlerts(game); // get prev alerts
        // culc the closest iceberg to put as targets
        List<Iceberg> optionToAttack = new ArrayList<>();
        for (Iceberg iceberg : game.getNeutralIcebergs()) {
            optionToAttack.add(iceberg); // add all naturl icebergs to attack
        }
        Knowledge knowledge = Knowledge.getInstance();
        if(knowledge.getPartInGameNumber() != 1) {
            for (Iceberg iceberg : game.getEnemyIcebergs()) {
                optionToAttack.add(iceberg); // add all enemys icebergs to attack
            }
        } else {
            for (Iceberg iceberg : game.getEnemyIcepitalIcebergs()) {
                //optionToAttack.add(iceberg);
            }
        }
        List<Iceberg> myIcebergs = new ArrayList<>();
        for (Iceberg iceberg : game.getMyIcebergs()) {
            myIcebergs.add(iceberg); // add my icebergs
        }
        if (optionToAttack.isEmpty()) {
            return attackList;
        }
        Vector<Pair<Iceberg, Double>> closests = getClosestIcebergToMe(myIcebergs, optionToAttack);
        Vector<Pair<Iceberg, Double>> IcbergsWithScale = new Vector<>();
        for (Pair<Iceberg, Double> toCalc : closests) {
            IcbergsWithScale.add(new Pair<>(toCalc.getFirst(), toCalc.getSecond() * toCalc.getFirst().penguinAmount));
            // clac distance divided by the number of peng in defence
        }
        // chose the target with best scale

        Pair<Iceberg, Double> closest = Collections.min(IcbergsWithScale, (a, b) -> {
            return (int) (a.getSecond() - b.getSecond());
        });
        // want to find the natural iceberg to attack so to avrage from all my icebergs
        while (iAttackIt(game, closest.getFirst())) { // if get in means that the iceberg is under attack from me and
                                                      // dont have to attack again
            IcbergsWithScale.remove(closest);
            if (IcbergsWithScale.size() == 0) {
                break;
            }
            closest = Collections.max(IcbergsWithScale, (a, b) -> {
                return (int) (b.getSecond() - a.getSecond());
            });
        }
        if (optionToAttack.isEmpty()) {
            closest = new Pair<>(game.getEnemyIcepitalIcebergs()[0], 0.0);
        }

        attackList.add(new CanAttack(closest.getFirst()));
        return attackList;
    }

    /**
     * this func find the 4 closest iceberg in toCheck with average distance from
     * the icebergs in mine
     * 
     * @param mine
     * @param toCheck
     * @return
     */
    private Vector<Pair<Iceberg, Double>> getClosestIcebergToMe(List<Iceberg> mine, List<Iceberg> toCheck) {
        Vector<Pair<Iceberg, Double>> toRet = new Vector<>();
        if (toCheck.size() == 0) {
            return null;
        }
        for (Iceberg target : toCheck) {
            double newDistance = 0;
            for (Iceberg myIce : mine) {
                newDistance += myIce.getTurnsTillArrival(target);
            }
            newDistance = newDistance / mine.size();
            if (toRet.size() < 4) {
                toRet.add(new Pair<>(target, newDistance));
            } else {
                Pair<Iceberg, Double> farest = Collections.max(toRet, (a, b) -> {
                    return (int) (a.getSecond() - b.getSecond());
                });
                if (newDistance < farest.getSecond()) {
                    // found closer iceberg to target
                    toRet.remove(farest);
                    toRet.add(new Pair<>(target, newDistance));
                }
            }

        }
        return toRet;
    }

    /**
     * this func checks if I allready attacking a iceberg so choose other target
     * 
     * @param game
     * @param target
     * @return
     */
    private boolean iAttackIt(Game game, Iceberg target) {
        if (target == null) {
            return false;
        }
        int numberOfAttacking = 0;
        for (PenguinGroup penguinGroup : game.getMyPenguinGroups()) {
            if (penguinGroup.destination == target) {
                numberOfAttacking += penguinGroup.penguinAmount;
            }
        }
        if (numberOfAttacking > target.penguinAmount) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "CanAttackAnalyze [attackOption=" + attackOption + "]\n";
    }

}
