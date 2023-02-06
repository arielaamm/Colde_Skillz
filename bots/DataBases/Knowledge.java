package bots.DataBases;

import bots.LongTimeProcess.LongTimeProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * this is a singleton!!!
 * here we save all the processes and the part number
 */
public class Knowledge {
    private static Knowledge knowledge;
    public static Knowledge getInstance() {
        if (knowledge == null) {
            knowledge = new Knowledge();
        }
        return knowledge;
    }
    private Knowledge() {
        allProcesses = new ArrayList<>();
        partInGameNumber = 0;
    }
    private List<LongTimeProcess> allProcesses;
    private int partInGameNumber; //can be 1/2/3

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
    public void nextPart() {
        partInGameNumber++;
    }
}
