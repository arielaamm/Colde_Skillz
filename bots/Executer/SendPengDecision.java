package bots.Executer;

import penguin_game.Iceberg;

public class SendPengDecision implements Executable {
    private Iceberg source;
    private Iceberg target;
    private int sum;

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

    @Override
    public void execute() {
        source.sendPenguins(target, sum);
    }

    @Override
    public String toString() {
        return "SendPengDecision [source=" + source.toString() + ", target=" + target.toString() + ", sum=" + sum
                + "]\n";
    }

}
