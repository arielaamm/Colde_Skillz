package bots.Executer;

import bots.DataBases.Knowledge;
import penguin_game.IceBuilding;
import penguin_game.PenguinGroup;

public class AccelerateDecision implements Executable {
    PenguinGroup penguinGroup;

    IceBuilding source;
    IceBuilding target;
    int amount;

    public AccelerateDecision(IceBuilding source, IceBuilding target, int amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    public AccelerateDecision(PenguinGroup group) {
        this.penguinGroup = group;
    }

    @Override
    public void execute() {
        if (penguinGroup != null) {
            penguinGroup.accelerate();
        } else {
            for (PenguinGroup penguinGroup : Knowledge.getGame().getAllPenguinGroups()) {
                if (penguinGroup.destination == target && penguinGroup.source == source
                        && penguinGroup.penguinAmount == amount) {
                    penguinGroup.accelerate();
                }
            }
        }
    }
}
