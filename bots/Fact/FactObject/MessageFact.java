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

    @Override
    public int hashCode() {
        int result = 0;
        result += ((info == null) ? 0 : info.hashCode());
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
        MessageFact other = (MessageFact) obj;
        if (info == null) {
            if (other.info != null)
                return false;
        } else if (!info.equals(other.info))
            return false;
        if (priority != other.priority)
            return false;
        return true;
    }
    
}
