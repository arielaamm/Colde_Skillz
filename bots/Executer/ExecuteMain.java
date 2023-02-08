package bots.Executer;

import bots.DataBases.Knowledge;
import bots.LongTimeProcess.LongTimeProcess;

import java.util.List;

public class ExecuteMain {
    public void execute(List<Executable> decision) {
        for (Executable executable : decision) {
            executable.execute(); //run the decisions of now
        }
        Knowledge knowledge = Knowledge.getInstance();
        List<LongTimeProcess> processes = knowledge.getAllProcesses();
        for (LongTimeProcess process : processes) {
            //run all the long time processes
            process.execute();
            process.endTurn();
        }
    }

}
