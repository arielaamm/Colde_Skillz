package bots.Analyze.IDS;

import bots.DataBases.Pair;
import bots.Facts.Alerts.UnderAttackAlert;
import bots.Facts.Alert;
import bots.Functions.NumOfAttackerCounter;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UnderAttackIDS extends IDS {
    IDS ids;

    public UnderAttackIDS(IDS ids) {
        this.ids = ids;
    }

    /*
     * NOTE: because of the line "List<Iceberg> sources = new ArrayList<>();"
     ** iceberg attacked from multiple locations will issue more than one alert
     * ANSWER: It is not true, read the code again
     */

    @Override
    public List<Alert> getAlerts(Game game) {
        List<Alert> alertList = ids.getAlerts(game); // get prev alerts
        for (Iceberg myIce : game.getMyIcebergs()) {
            boolean underAttack = false;
            Vector<Pair<Integer, Integer>> NextGame = NumOfAttackerCounter.getNumberOfAttackers(myIce, game);
            for (Pair<Integer, Integer> turn : NextGame) {
                if (turn.getFirst() > turn.getSecond()) {
                    underAttack = true;
                    break;
                }
            }
            if (!underAttack) {
                continue;
            }
            // for each iceberg of mine will check if under attack
            List<Iceberg> sources = new ArrayList<>();
            List<PenguinGroup> penguinGroupsAttackers = new ArrayList<>();
            for (PenguinGroup attackers : game.getEnemyPenguinGroups()) {
                // check the enemy ping groups if they attack the iceberg
                if (attackers.destination == myIce) {
                    if (!sources.contains(attackers.source)) {
                        sources.add(attackers.source);
                    }
                    penguinGroupsAttackers.add(attackers);
                    // create the Alert and add it to the list
                }
            }
            if (sources.size() != 0) {
                alertList.add(new UnderAttackAlert(myIce, sources, penguinGroupsAttackers));
            }
        }
        return alertList;
    }

    @Override
    public String toString() {
        return "UnderAttackIDS [ids=" + ids + "]\n";
    }

}
