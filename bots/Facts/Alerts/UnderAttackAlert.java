package bots.Facts.Alerts;
import bots.Facts.Alert;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.List;

public class UnderAttackAlert extends Alert {
    private Iceberg target;
    private List<Iceberg> sources;
    private List<PenguinGroup> attackers;

    public UnderAttackAlert(Iceberg target, List<Iceberg> sources, List<PenguinGroup> attackers) {
        this.target = target;
        this.sources = sources;
        this.attackers = attackers;
    }

    public Iceberg getTarget() {
        return target;
    }

    @Override
    public String getDescription() {
        return "UnderAttack";
    }

    public List<PenguinGroup> getAttackers() {
        return attackers;
    }
}
