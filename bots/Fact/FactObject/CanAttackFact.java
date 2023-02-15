package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;
import penguin_game.Iceberg;

public class CanAttackFact implements Fact {
    private Iceberg target;
    private Iceberg source;
    private String info = "";
    private PriorityEnum priority;
    private int penguinAmount;
    
    

    public CanAttackFact(Iceberg target, Iceberg source, String info, PriorityEnum priority, int penguinAmount) {
        this.target = target;
        this.source = source;
        this.info = info;
        this.priority = priority;
        this.penguinAmount = penguinAmount;
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

    @Override
    public String toString() {
        return "CanAttackFact [target=" + target + ", source=" + source + ", info=" + info + ", priority=" + priority
                + ", penguinAmount=" + penguinAmount + "]";
    }
}
