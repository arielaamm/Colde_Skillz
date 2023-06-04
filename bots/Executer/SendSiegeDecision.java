package bots.Executer;

import bots.DataBases.Knowledge;
import penguin_game.Game;
import penguin_game.Iceberg;

public class SendSiegeDecision extends SendPengDecision{

    public SendSiegeDecision(Iceberg source, Iceberg target, int amount) {
        super(source, target, amount);
    }

    @Override
    public void execute() {
        source.sendPenguinsToSetSiege(target, sum);
    }
}
