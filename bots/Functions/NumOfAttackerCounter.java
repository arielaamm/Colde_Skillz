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
     * @return
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
        int lastAttack = 0;
        for (PenguinGroup penguinGroup : attackers) {
            if (penguinGroup.turnsTillArrival > lastAttack) {
                lastAttack = penguinGroup.turnsTillArrival; //find last attack
            }
        }
        Vector<Pair<Integer, Integer>> toRet = new Vector<>();
        for (int i = 0; i < lastAttack + 2 ; i++) {
            Pair<Integer, Integer> a = new Pair<>(0, target.penguinAmount + target.penguinsPerTurn * (i- 1));
            for (PenguinGroup def : defender){
                if (def.turnsTillArrival <= i) {
                    a = new Pair<>(a.getFirst(), a.getSecond() + def.penguinAmount);
                }
            }
            for (PenguinGroup att : attackers) {
                if (att.turnsTillArrival < i) {
                    a = new Pair<>(a.getFirst() + att.penguinAmount, a.getSecond());
                }
            }
            toRet.add(i, a);
        }
        //[ ] calc the Long Time Process impact on the number of pengs in the iceberg
        return toRet;
    }
}
