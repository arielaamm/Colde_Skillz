package bots.Fact.FactObject;

public class MessageFact implements Fact{
    private String info = "";
    private int priority;
    

    
    public MessageFact(String info, int priority) {
        this.info = info;
        this.priority = priority;
    }

    @Override
    public int getPriority() {
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
