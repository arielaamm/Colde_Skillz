package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;
import penguin_game.Iceberg;

public class CanAttackFact implements Fact {
    private Iceberg target;
    private Iceberg source;
    private String info = "";
    private PriorityEnum priority;
    private int penguinInIceberg;
    private int penguinAmountNeeded;

    public CanAttackFact(Iceberg target, Iceberg source, String info, PriorityEnum priority, int penguinInIceberg, int penguinAmountNeeded) {
        this.target = target;
        this.source = source;
        this.info = info;
        this.priority = priority;
        this.penguinInIceberg = penguinInIceberg;
        this.penguinAmountNeeded = penguinAmountNeeded;
    }

    @Override
    public String getDescription() {
        return info;
    }

    public Iceberg getTarget() {
        return target;
    }

    @Override
    public PriorityEnum getPriority() {
        return priority;
    }

    public Iceberg getSource() {
        return source;
    }

    public int getPenguinInIceberg() {
        return penguinInIceberg;
    }

    public int getPenguinAmountNeeded() {
        return penguinAmountNeeded;
    }

    @Override
    public String toString() {
        return "CanAttackFact [target=" + target + ", source=" + source + ", info=" + info + ", priority=" + priority
                + ", penguinInIceberg=" + penguinInIceberg + ", penguinAmountNeeded=" + penguinAmountNeeded + "]";
    }

}
