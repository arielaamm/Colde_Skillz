package bots.Fact.FactObject;

import penguin_game.Iceberg;

public class MessageFact implements Fact{
    Iceberg source;
    String info = "";
    int priority;
    
    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public Iceberg getSource() {
        return source;
    }

    @Override
    public String getDescription() {
        return info;
    }

    @Override
    public String toString() {
        return "MessageFact [source=" + source + ", info=" + info + ", priority=" + priority + "]";
    }
    
}
