package bots.Functions;

import bots.DataBases.Pair;
import penguin_game.Game;
import penguin_game.Iceberg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class DistanceFunctions {
    /**
     * this func  calc the average distance from the centers and sort the options by
     * the distance
     * 
     * @param centers
     * @param toSort
     * @return
     */
    public static Vector<Pair<Iceberg, Double>> sortIcebegByDistance(List<Iceberg> centers, List<Iceberg> toSort) {
        Vector<Pair<Iceberg, Double>> toRet = new Vector<>();
        for (Iceberg calc : toSort) {
            Double distance = 0.0;
            for (Iceberg center : centers) {
                distance += center.getTurnsTillArrival(calc);
            }
            distance /= centers.size();
            toRet.add(new Pair<>(calc, distance));
        }
        Collections.sort(toRet, (a, b) -> {
            return (int) (a.getSecond() - b.getSecond());
        });
        return toRet;
    }

    public static Vector<Pair<Iceberg, Double>> sortIcebegByDistance(Iceberg centers, List<Iceberg> toSort) {
        List<Iceberg> list =  new LinkedList<Iceberg>();
        list.add(centers);
        return sortIcebegByDistance(list , toSort);
    }

}
