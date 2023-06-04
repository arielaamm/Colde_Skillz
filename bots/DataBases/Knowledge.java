package bots.DataBases;

import bots.LongTimeProcess.LongTimeProcess;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.*;

/**
 * this is a singleton!!!
 * here we save all the processes and the part number
 */
public class Knowledge {
    private static Knowledge knowledge;
    private List<LongTimeProcess> allProcesses;
    private int partInGameNumber = 1; //can be 1/2/3

    private static Game game;
    private static List<Iceberg> closest;
    private List<Iceberg> naturalWeTaking = new ArrayList<>();
    public void updateNaturalWeTaking() {
        //add all icbergs i attack now
        for (PenguinGroup penguinGroup : game.getMyPenguinGroups()) {
            if (penguinGroup.destination.owner == game.getNeutral()) {
                if (!naturalWeTaking.contains((Iceberg) penguinGroup.destination)) {
                    naturalWeTaking.add((Iceberg) penguinGroup.destination);
                }
            }
        }
        //remove all icebergs allready taken
        Iterator<Iceberg> iterator = naturalWeTaking.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().owner != game.getNeutral()) {
                iterator.remove();
            }
        }
    }

    public List<Iceberg> getNaturalWeTaking() {
        return naturalWeTaking;
    }

    public static Knowledge getInstance() {
        if (knowledge == null) {
            knowledge = new Knowledge();
        }
        return knowledge;
    }
    private Knowledge() {
        allProcesses = new ArrayList<>();
        partInGameNumber = 1;
        closest = new LinkedList<>();
    }


    public int getPartInGameNumber() {
        return partInGameNumber;
    }

    public List<LongTimeProcess> getAllProcesses() {
        return allProcesses;
    }

    public void addProcess(LongTimeProcess process) {
        allProcesses.add(process);
    }
    public void removeProcess(LongTimeProcess process) {
        allProcesses.remove(process);
    }
    public void ProceedToNextPart() {
        partInGameNumber++;
    }

    public static Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        Knowledge.game = game;
    }
    public static List<Iceberg> getClosest() {
        return closest;
    }
    public static void setClosest(Vector<Pair<Iceberg, Double>> closest) {
        Knowledge.closest.clear();
        for (int i = 0; i < 2; i++) {
            Knowledge.closest.add(closest.get(i).getFirst());
        }
    }
    @Override
    public String toString() {
        return "Knowledge [allProcesses=" + allProcesses + ", partInGameNumber=" + partInGameNumber + "]";
    }
    
}
