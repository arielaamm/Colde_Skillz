package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;
import penguin_game.*;

public class UnderAttackFact implements Fact{
    private String info = "";
    private PriorityEnum priority;
    private PenguinGroup penguinGroup;


    
    public UnderAttackFact(String info, PriorityEnum priority, PenguinGroup penguinGroup) {
        this.info = info;
        this.priority = priority;
        this.penguinGroup = penguinGroup;
    }

    public PenguinGroup getPenguinGroup() {
        return penguinGroup;
    }

    @Override
    public PriorityEnum getPriority() {
        return priority;
    }
    
    @Override
    public String getDescription() {
        return info;
    }

    @Override
    public String toString() {
        return "ActionFact [info=" + info + ", priority=" + priority + ", penguinGroup=" + penguinGroup + "]";
    }




}
