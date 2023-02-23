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
    private List<LongTimeProcess> allProcesses;
    private int partInGameNumber = 1; //can be 1/2/3
    public static Knowledge getInstance() {
        if (knowledge == null) {
            knowledge = new Knowledge();
        }
        return knowledge;
    }
    private Knowledge() {
        allProcesses = new ArrayList<>();
        partInGameNumber = 1;
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
    @Override
    public String toString() {
        return "Knowledge [allProcesses=" + allProcesses + ", partInGameNumber=" + partInGameNumber + "]";
    }
    
}