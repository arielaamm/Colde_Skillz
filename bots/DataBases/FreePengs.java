package bots.DataBases;

import bots.Executer.AccelerateDecision;
import bots.Executer.Executable;
import bots.Executer.SendPengDecision;
import bots.Executer.UpgradeIcebergDecision;
import bots.Functions.NumOfAttackerCounter;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;
import penguin_game.Player;

import java.util.*;

public class FreePengs {
    private Map<Iceberg, Vector<Pair<Integer, Integer>>> map;
    private List<Executable> wayFromBase;
    public FreePengs(Game game) {
        map = new HashMap<>();
        for (Iceberg mine : game.getMyIcebergs()) {
            Vector<Pair<Integer, Integer>> nextTurns = NumOfAttackerCounter.getNumberOfAttackers(mine, game);
            game.debug("Iceberg: " + mine.toString() +"nextTurns: " + nextTurns.toString());
            map.put(mine, nextTurns);
        }
        wayFromBase = new ArrayList<>();
    }
    public void update(Executable executable) {
        if (executable instanceof  SendPengDecision) {
            update((SendPengDecision) executable);
        }
        if (executable instanceof UpgradeIcebergDecision) {
            update((UpgradeIcebergDecision) executable);
        }
        if (executable instanceof AccelerateDecision) {
            update((AccelerateDecision) executable);
        }
    }

    private void update(SendPengDecision sendPengDecision) {
        map.put(sendPengDecision.getSource(), NumOfAttackerCounter.getNumberOfAttackers(sendPengDecision.getSource(), Knowledge.getGame(), sendPengDecision));
        map.put(sendPengDecision.getTarget(), NumOfAttackerCounter.getNumberOfAttackers(sendPengDecision.getTarget(), Knowledge.getGame(), sendPengDecision));
    }
    private void update(UpgradeIcebergDecision upgradeIcebergDecision) {
        map.put(upgradeIcebergDecision.getToUpgrade(), NumOfAttackerCounter.getNumberOfAttackers(upgradeIcebergDecision.getToUpgrade(), Knowledge.getGame(), upgradeIcebergDecision));
    }
    private void update(AccelerateDecision accelerateDecision) {
        map.put((Iceberg) accelerateDecision.getSource(), NumOfAttackerCounter.getNumberOfAttackers((Iceberg) accelerateDecision.getSource(), Knowledge.getGame(), accelerateDecision));
        map.put((Iceberg) accelerateDecision.getTarget(), NumOfAttackerCounter.getNumberOfAttackers((Iceberg) accelerateDecision.getTarget(), Knowledge.getGame(), accelerateDecision));

    }
    public Integer get(Iceberg iceberg) {
        Vector<Integer> free = new Vector<>();
        for (Pair<Integer, Integer> number : map.get(iceberg)) {
            free.add(- number.getSecond() + number.getFirst());
        }
        int minimumFree = 0;
        if (!free.isEmpty()) {
            minimumFree = Collections.min(free);
        } else {
            minimumFree = iceberg.penguinAmount;
        }
        return minimumFree;
    }
    public FreePengs explore(Executable executable) throws CloneNotSupportedException {
        FreePengs freePengs = (FreePengs) this.clone();
        freePengs.update(executable);
        freePengs.wayFromBase.add(executable);
        return freePengs;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FreePengs freePengs = new FreePengs(Knowledge.getGame());
        freePengs.map = new HashMap<>();
        for (Iceberg iceberg : map.keySet()) {
            Vector<Pair<Integer, Integer>> vector = new Vector<>();
            for (Pair<Integer, Integer> pair : map.get(iceberg)) {
                vector.add((Pair<Integer, Integer>) pair.clone());
            }
            map.put(iceberg, vector);
        }

        freePengs.wayFromBase = new ArrayList<>();
        for (Executable executable : wayFromBase) {
            freePengs.wayFromBase.add(executable);
        }
        return freePengs;
    }

    public static int AmountAtIceberg(Iceberg iceberg, Game game) {
        if (!(iceberg instanceof Iceberg)) {
            return 0;
        }
        List<PenguinGroup> allPenguinGroups = new ArrayList<>();
        for (PenguinGroup i : game.getAllPenguinGroups()) {
            allPenguinGroups.add(i);
        }
        Collections.sort(allPenguinGroups, (a,b) -> {return (int)(a.turnsTillArrival - b.turnsTillArrival);});
        int amout = iceberg.penguinAmount;
        for (int i = 0; i < 40; i++) {
            for (PenguinGroup penguinGroup : allPenguinGroups) {
                if (penguinGroup.destination == iceberg) {
                    if (penguinGroup.destination.owner == game.getMyself()) {
                        amout += penguinGroup.penguinAmount;
                    } else {
                        amout -= penguinGroup.penguinAmount;
                    }
                }
            }
            if (amout > 0) {
                amout += ((Iceberg) iceberg).penguinsPerTurn;
            } else {
                amout -= ((Iceberg) iceberg).penguinsPerTurn;;
            }
        }

        return amout;
    }

    public Player ownerAtTheEnd(Iceberg iceberg) {

        if (map.get(iceberg).get(map.get(iceberg).size() - 1).getFirst() > map.get(iceberg).get(map.get(iceberg).size() - 1).getSecond()) {
            return Knowledge.getGame().getMyself();
        } else {
            return Knowledge.getGame().getEnemy();
        }
    }

    public Map<Iceberg, Vector<Pair<Integer, Integer>>> getMap() {
        return map;
    }

    public List<Executable> getWayFromBase() {
        return wayFromBase;
    }
}
