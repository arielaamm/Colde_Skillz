package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;
import penguin_game.*;

public class CanUpgradeMessage extends MessageFact {
    IceBuilding source;

    public CanUpgradeMessage(String info, PriorityEnum priority, IceBuilding source) {
        super(info, priority);
        this.source = source;
    }

    public IceBuilding getSource() {
        return source;
    }

    @Override
    public String toString() {
        return super.toString() + "CanUpgradeMessage [source=" + source + "]";
    }

    
}
