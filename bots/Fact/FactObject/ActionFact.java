package bots.Fact.FactObject;

import penguin_game.Iceberg;

public class ActionFact implements Fact{
    private Iceberg source;
    private Iceberg target;
    private String info = "";
    private int priority;
    private int amount;


    
    public ActionFact(Iceberg source, Iceberg target, String info, int priority, int amount) {
        this.source = source;
        this.target = target;
        this.info = info;
        this.priority = priority;
        this.amount = amount;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public Iceberg getSource() {
        return source;
    }

    @Override
    public String getDescription() {
        return info;
    }

    public Iceberg getTarget() {
        return target;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ActionFact [source=" + source + ", target=" + target + ", info=" + info + ", priority=" + priority + ", amount=" + amount + "]";
    }
}
