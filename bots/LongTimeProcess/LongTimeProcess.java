package bots.LongTimeProcess;

import bots.Exeuter.Executable;

import java.util.Map;

public class LongTimeProcess implements Executable{
    protected Map<Integer, Executable> process; //in how many turn from now to what to do
    protected String description = "";
    public String getDescription() {
        return description;
    }
    public void addProcess(Executable task, int turn) {
        process.put(turn, task);
    }
    public void removeProcess(int turn) {
        process.remove(turn);
    }

    /**
     * move all the missions one part forward
     */
    public void endTurn(){
        process.forEach((key, value) -> process.put(key - 1, value));
        /*Map<Integer, Executable> newOne = new HashMap<>();
        for(Integer turn : process.keySet()) {
            newOne.put(turn - 1, process.get(turn));
        }
        process = newOne;*/
    }

    /**
     * runs the process that need to be execute
     */
    @Override
    public void execute() {
        if (process.containsKey(0)) {
            process.get(0).execute();
        }
    }
}
