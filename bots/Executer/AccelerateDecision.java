package bots.Executer;

import bots.DataBases.Knowledge;
import penguin_game.IceBuilding;
import penguin_game.PenguinGroup;

public class AccelerateDecision implements Executable {
    //NOTE: change it a little so look on it
    private PenguinGroup penguinGroup;

    private IceBuilding source;
    private IceBuilding target;
    private int amount;
    private int theNumberTimeOfAcc;

    public AccelerateDecision(IceBuilding source, IceBuilding target, int amount, int theNumberTimeOfAcc) {
        this.source = source;
        this.target = target;
        this.amount = amount;
        this.theNumberTimeOfAcc = theNumberTimeOfAcc;
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


    public IceBuilding getSource() {
        if (source == null) {
            return penguinGroup.source;
        }
        return source;
    }

    public IceBuilding getTarget() {
        if (target == null) {
            return penguinGroup.destination;
        }
        return target;
    }

    public PenguinGroup getPenguinGroup() {
        return penguinGroup;
    }

    public int getAmount() {
        if (amount == 0) {
            return penguinGroup.penguinAmount;
        }
        return amount;
    }

    public int getTheNumberTimeOfAcc() {
        return theNumberTimeOfAcc;
    }
}
