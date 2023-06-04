package bots.Executer;

import penguin_game.Cloneberg;
import penguin_game.IceBuilding;
import penguin_game.Iceberg;

public class SendPengDecision implements Executable {
    protected Iceberg source;
    protected Iceberg target;
    private Cloneberg targetCloneberg;
    protected int sum;

    /**
     * @param source - {@link Iceberg}: the source iceberg
     * @param target - {@link Iceberg}: the target iceberg
     * @param sum    - {@code int}: the number of Penguin to send
     */
    public SendPengDecision(Iceberg source, Iceberg target, int sum) {
        this.source = source;
        this.target = target;
        this.sum = sum;
    }
    public SendPengDecision(Iceberg source, Cloneberg target, int sum) {
        this.source = source;
        this.targetCloneberg = target;
        this.sum = sum;
    }
    @Override
    public void execute() {
        if(targetCloneberg != null) {
            source.sendPenguins(targetCloneberg, sum);
        } else {
            source.sendPenguins(target, sum);
        }
    }

    @Override
    public String toString() {
        if (targetCloneberg != null) {
            return "SendPengDecision [source=" + source.toString() + ", target=" + targetCloneberg.toString() + ", sum=" + sum
                    + "]\n";
        } else {
            return "SendPengDecision [source=" + source.toString() + ", target=" + target.toString() + ", sum=" + sum
                    + "]\n";
        }
    }

    public Iceberg getSource() {
        return source;
    }
    public Iceberg getTarget() {
        return target;
    }

    public int getSum() {
        return sum;
    }
}
