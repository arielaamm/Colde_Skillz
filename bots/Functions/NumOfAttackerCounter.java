package bots.Functions;

import bots.DataBases.Pair;
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
    public static Vector<Pair<Integer, Integer>> getNumberOfAttackers(Iceberg target, Game game) {
        List<PenguinGroup> interestingGroups = new ArrayList<>(); //will be a sub list of all peng groups that will get to the target
        for (PenguinGroup penguinGroup : game.getAllPenguinGroups()) {
            if (penguinGroup.destination == target) {
                interestingGroups.add(penguinGroup);
            }
        }
        List<PenguinGroup> attackers = new ArrayList<>(); //the attackers from the interesting
        List<PenguinGroup> defender = new ArrayList<>(); //the defender from the interesting
        for (PenguinGroup penguinGroup : interestingGroups) {
            if (penguinGroup.owner == target.owner) {
                defender.add(penguinGroup);
            } else {
                attackers.add(penguinGroup);
            }
        }
        int lastAttack = 20;
//        for (PenguinGroup penguinGroup : attackers) {
//            if (penguinGroup.turnsTillArrival > lastAttack) {
//                lastAttack = penguinGroup.turnsTillArrival; //find last attack
//            }
//        }
        Vector<Pair<Integer, Integer>> toRet = new Vector<>();
        if (target.owner == game.getMyself()){
            toRet.add(new Pair<>(target.penguinAmount, 0));
        } else {
            toRet.add(new Pair<>(0, target.penguinAmount));
        }

        for (int i = 0; i < lastAttack + 2 ; i++) {
            int first, second;
            Pair<Integer, Integer> lastTurn = toRet.get(i);
            if (lastTurn.getFirst() > lastTurn.getSecond()){
                //in this turn th e iceberg is mine
                first = lastTurn.getFirst() + target.penguinsPerTurn;
                second = lastTurn.getSecond();
            } else {
                first = lastTurn.getFirst();
                second = lastTurn.getSecond() + target.penguinsPerTurn;
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
}
