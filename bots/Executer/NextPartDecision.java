package bots.Executer;

import bots.DataBases.Knowledge;

public class NextPartDecision implements Executable{

    @Override
    public void execute() {
        Knowledge knowledge = Knowledge.getInstance();
        knowledge.ProceedToNextPart();
    }
}
