package bots.Fact.FactObject;

import penguin_game.Iceberg;

public class ActionFact implements Fact{
    Iceberg source;
    Iceberg target;
    String info = "";
    int priority;
    int amount;

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

    public Iceberg getTarget() {
        return target;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ActionFact [source=" + source + ", target=" + target + ", info=" + info + ", priority=" + priority
                + ", amount=" + amount + "]";
    }
}
