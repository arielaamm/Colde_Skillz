package bots.Fact.FactObject;

import penguin_game.Iceberg;

public class CanUpgradeMessage extends MessageFact {
    Iceberg source;

    public CanUpgradeMessage(String info, int priority, Iceberg source) {
        super(info, priority);
        this.source = source;
    }

    public Iceberg getSource() {
        return source;
    }

    @Override
    public String toString() {
        return super.toString() + "CanUpgradeMessage [source=" + source + "]";
    }

    
}
