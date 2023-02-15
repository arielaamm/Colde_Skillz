package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;

public interface Fact {
    public PriorityEnum getPriority();
    public String getDescription();
}
