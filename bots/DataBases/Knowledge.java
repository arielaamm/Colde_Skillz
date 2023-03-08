package bots.DataBases;

import bots.Functions.DistanceFunctions;
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
    private int partInGameNumber = 1; // can be 1/2/3

    private static Game game;
    private static List<Iceberg> belt;
    private static double accelerationCost;
    private boolean isBeltOurs;

    public static Knowledge getInstance() {
        if (knowledge == null) {
            knowledge = new Knowledge();
        }
        return knowledge;
    }

    private Knowledge() {
        allProcesses = new ArrayList<>();
        partInGameNumber = 1;
        belt = new LinkedList<>();
        isBeltOurs = false;
    }


    public void endTurn() {
        for (LongTimeProcess longTimeProcess : allProcesses) {
            longTimeProcess.setTurn(longTimeProcess.getTurn()-1);
        }
        allProcesses.removeIf((a) -> a.getTurn() <= 0);
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
        Knowledge.accelerationCost = game.accelerationCost;
        List<Iceberg>  getMyIcepitalIcebergs = new ArrayList<>();
        for (Iceberg iceberg : game.getMyIcepitalIcebergs()) {
            getMyIcepitalIcebergs.add(iceberg);
        }
        List<Iceberg>  getAllIcebergs = new ArrayList<>();
        for (Iceberg iceberg : game.getAllIcebergs()) {
            getAllIcebergs.add(iceberg);
        }
        setBelt(DistanceFunctions.sortIcebegByDistance(getMyIcepitalIcebergs,getAllIcebergs));
        boolean isBeltTaken = true;
        for (Iceberg iceberg : belt) {
            if (iceberg.owner != game.getMyself()) {
                isBeltTaken = false;
            }
        }
        if (isBeltTaken) {
            isBeltOurs = true;
        }
    }

    public static List<Iceberg> getBeltIceberg() {
        return belt;
    }

    private  void setBelt(Vector<Pair<Iceberg, Double>> belt) {
        for (int i = 0; i < 2; i++) {
            Knowledge.belt.add(belt.get(i).getFirst());
        }
    }

    @Override
    public String toString() {
        return "Knowledge [allProcesses=" + allProcesses + ", partInGameNumber=" + partInGameNumber + "]";
    }


    public boolean isBeltOurs() {
        return isBeltOurs;
    }
    public static boolean canAcclerate() {
        return game.accelerationCost != 0;
    }
}
