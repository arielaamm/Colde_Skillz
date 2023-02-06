package bots.Exeuter;

import penguin_game.Iceberg;

public class SendPeng implements Executable{
    private Iceberg source;
    private Iceberg target;
    private int sum;

    public SendPeng(Iceberg source, Iceberg target, int sum) {
        this.source = source;
        this.target = target;
        this.sum = sum;
    }

    @Override
    public void execute() {
        source.sendPenguins(target, sum);
    }
}
