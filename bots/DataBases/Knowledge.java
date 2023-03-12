package bots.DataBases;

import bots.LongTimeProcess.LongTimeProcess;
import penguin_game.Game;
import penguin_game.Iceberg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

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
