package bots.Fact.FactObject;

import bots.Fact.PriorityEnum;
import penguin_game.*;

public class UnderAttackFact implements Fact {
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

    @Override
    public int hashCode() {
        int result = 0;
        if (info != null) {
            result += info.hashCode();
        }
        if (penguinGroup != null) {
            result += penguinGroup.destination.hashCode();
            result += penguinGroup.penguinAmount;
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UnderAttackFact other = (UnderAttackFact) obj;
        if (info == null) {
            if (other.info != null)
                return false;
        } else if (!info.equals(other.info))
            return false;
        if (priority != other.priority)
            return false;
        if (penguinGroup == null) {
            if (other.penguinGroup != null)
                return false;
        } else if (!penguinGroup.equals(other.penguinGroup))
            return false;
        return true;
    }

}
