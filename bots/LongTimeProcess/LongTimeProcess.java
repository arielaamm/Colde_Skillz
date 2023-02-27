package bots.LongTimeProcess;

import bots.Executer.Executable;

public class LongTimeProcess implements Executable {
    protected int turn;
    protected Executable task;
    protected String description = "";

    public String getDescription() {
        return description;
    }

    public LongTimeProcess(int turn, Executable task, String description) {
        this.turn = turn;
        this.task = task;
        this.description = description;
    }

    /**
     * runs the process that need to be execute
     */
    @Override
    public void execute() {
        if (turn == 0) {
            task.execute();
        }
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Executable getTask() {
        return task;
    }

    public void setTask(Executable task) {
        this.task = task;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LongTimeProcess [turn=" + turn + ", task=" + task + ", description=" + description + "]";
    }

}
