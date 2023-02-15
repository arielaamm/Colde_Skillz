package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;

public class MessageFact implements Fact{
    private String info = "";
    private PriorityEnum priority;
    
    public MessageFact(String info, PriorityEnum priority) {
        this.info = info;
        this.priority = priority;
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
        return "MessageFact [info=" + info + ", priority=" + priority + "]";
    }
    
}
