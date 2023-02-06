package bots.LongTimeProcess;

import bots.Exeuter.Executable;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class LongTimeProcess implements Executable{
    protected Map<Integer, Executable> process; //in how many turn from now to what to do
    protected String description;
    public String getDescription() {
        return description;
    }
    public void addTurn(Executable task, int turn) {
        process.put(turn, task);
    }
    public void removeTurn(int turn) {
        process.remove(turn);
    }

    /**
     * this func move all the missions one part forward
     */
    public void endTurn(){
        Map<Integer, Executable> newOne = new HashMap<>();
        for(Integer turn : process.keySet()) {
            newOne.put(turn - 1, process.get(turn));
        }
        process = newOne;
    }

    /**
     * runs the turn for now
     */
    @Override
    public void execute() {
        if (process.containsKey(0)) {
            process.get(0).execute();
        }
    }
}
