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
    private Map<Iceberg, Vector<Pair<Integer, Integer>>> natural;
    private List<Executable> wayFromBase;
    public FreePengs(Game game) {
        game.debug("constructor freePeng: ");
        map = new HashMap<>();
        List<Iceberg> list = new ArrayList<>();
        for (Iceberg iceberg : game.getMyIcebergs()) {
            list.add(iceberg);
        }
        for (Iceberg iceberg : game.getEnemyIcebergs()) {
            list.add(iceberg);
        }
        for (Iceberg mine : list) {
            Vector<Pair<Integer, Integer>> nextTurns = NumOfAttackerCounter.getNumberOfAttackers(mine, game);
            game.debug("Iceberg: " + mine.toString() +"nextTurns: " + nextTurns.toString());
            map.put(mine, nextTurns);
        }
        wayFromBase = new ArrayList<>();

        //do all to the natural
        natural = new HashMap<>();
        for (Iceberg nat : game.getNeutralIcebergs()) {
            natural.put(nat, NumOfAttackerCounter.getNumberOfAttackersNatural(nat, game, (Executable) null));
        }
        game.debug("end constructor");
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
        List<Executable> executableList = wayFromBase;
        executableList.add(sendPengDecision);
        map.put(sendPengDecision.getSource(), NumOfAttackerCounter.getNumberOfAttackers(sendPengDecision.getSource(), Knowledge.getGame(), executableList));
        if (sendPengDecision.getTarget().owner == Knowledge.getGame().getNeutral()) {
            natural.put(sendPengDecision.getTarget(), NumOfAttackerCounter.getNumberOfAttackersNatural(sendPengDecision.getTarget(), Knowledge.getGame(), executableList));
        } else {
            map.put(sendPengDecision.getTarget(), NumOfAttackerCounter.getNumberOfAttackers(sendPengDecision.getTarget(), Knowledge.getGame(), executableList));
        }
    }
    private void update(UpgradeIcebergDecision upgradeIcebergDecision) {
        List<Executable> executableList = wayFromBase;
        executableList.add(upgradeIcebergDecision);
        map.put(upgradeIcebergDecision.getToUpgrade(), NumOfAttackerCounter.getNumberOfAttackers(upgradeIcebergDecision.getToUpgrade(), Knowledge.getGame(), executableList));
    }
    private void update(AccelerateDecision accelerateDecision) {
        List<Executable> executableList = wayFromBase;
        executableList.add(accelerateDecision);
        map.put((Iceberg) accelerateDecision.getSource(), NumOfAttackerCounter.getNumberOfAttackers((Iceberg) accelerateDecision.getSource(), Knowledge.getGame(), executableList));
        map.put((Iceberg) accelerateDecision.getTarget(), NumOfAttackerCounter.getNumberOfAttackers((Iceberg) accelerateDecision.getTarget(), Knowledge.getGame(), executableList));
        //[ ]: cant acc if target is natural iceberg
    }
    public Integer get(Iceberg iceberg) {
        Vector<Integer> free = new Vector<>();
        if (iceberg.owner != Knowledge.getGame().getNeutral()) {
            if (map.get(iceberg) == null) {
                return iceberg.penguinAmount;
            }
            for (Pair<Integer, Integer> number : map.get(iceberg)) {
                free.add(-number.getSecond() + number.getFirst());
            }
        } else {
            for (Pair<Integer, Integer> number : natural.get(iceberg)) {
                free.add(-number.getSecond() + number.getFirst());
            }
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
        for (Executable executable : wayFromBase) {
            freePengs.update(executable);
            freePengs.wayFromBase.add(executable);
        }
        return freePengs;
    }


    public Player ownerAtTheEnd(Iceberg iceberg) {
        if(map.get(iceberg) == null) {
            if (natural.get(iceberg) == null) {
                return  Knowledge.getGame().getNeutral();
            }
            if (natural.get(iceberg).lastElement().getFirst() == 0 && natural.get(iceberg).lastElement().getSecond() == 0) {
                return Knowledge.getGame().getNeutral();
            }
            if (natural.get(iceberg).lastElement().getFirst() > natural.get(iceberg).lastElement().getSecond()) {
                return Knowledge.getGame().getMyself();
            }
            return Knowledge.getGame().getEnemy();
        }

        if (map.get(iceberg).get(map.get(iceberg).size() - 1).getFirst() > map.get(iceberg).get(map.get(iceberg).size() - 1).getSecond()) {
            return Knowledge.getGame().getMyself();
        } else {
            return Knowledge.getGame().getEnemy();
        }
    }

    public Map<Iceberg, Vector<Pair<Integer, Integer>>> getNaturalMap() {
        return natural;
    }

    public Map<Iceberg, Vector<Pair<Integer, Integer>>> getMap() {
        return map;
    }

    public List<Executable> getWayFromBase() {
        return wayFromBase;
    }
}
