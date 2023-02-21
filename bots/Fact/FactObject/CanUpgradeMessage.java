package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;
import penguin_game.*;

public class CanUpgradeMessage extends MessageFact {
    Iceberg source;

    public CanUpgradeMessage(String info, PriorityEnum priority, Iceberg source) {
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

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += ((source == null) ? 0 : source.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CanUpgradeMessage other = (CanUpgradeMessage) obj;
        if (source == null) {
            if (other.source != null)
                return false;
        }
        return true;
    }
}
