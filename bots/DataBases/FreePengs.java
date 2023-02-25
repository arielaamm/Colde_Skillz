package bots.DataBases;

import bots.Executer.SendPengDecision;
import bots.Executer.UpgradeIcebergDecision;
import bots.Functions.NumOfAttackerCounter;
import penguin_game.Game;
import penguin_game.Iceberg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class FreePengs {
    private Map<Iceberg, Integer> map;
    public FreePengs(Game game) {
        map = new HashMap<>();
        for (Iceberg mine : game.getMyIcebergs()) {
            Vector<Pair<Integer, Integer>> nextTurns = NumOfAttackerCounter.getNumberOfAttackers(mine, game);
            game.debug("Iceberg: " + mine.toString() +"nextTurns: " + nextTurns.toString());
            Vector<Integer> free = new Vector<>();
            for (Pair<Integer, Integer> number : nextTurns) {
                free.add(number.getSecond() - number.getFirst());
            }
            int minimumFree = 0;
            if (!free.isEmpty()) {
                minimumFree = Collections.min(free);
            } else {
                minimumFree = mine.penguinAmount;
            }
            map.put(mine, minimumFree);
        }
    }

    public void update(SendPengDecision sendPengDecision) {
        map.put(sendPengDecision.getSource(), map.get(sendPengDecision.getSource()) - sendPengDecision.getSum());
    }
    public void update(UpgradeIcebergDecision upgradeIcebergDecision) {
        map.put(upgradeIcebergDecision.getToUpgrade(), map.get(upgradeIcebergDecision.getToUpgrade()) - upgradeIcebergDecision.getToUpgrade().upgradeCost);
    }
    public Integer get(Iceberg iceberg) {
        return map.get(iceberg);
    }
}
