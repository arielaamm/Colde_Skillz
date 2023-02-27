package bots.Facts.Attacks;

import bots.Facts.Attack;
import penguin_game.Iceberg;

public class CanUpgrade extends Attack {
    private Iceberg iceberg;

    public CanUpgrade(Iceberg iceberg) {
        this.iceberg = iceberg;
    }

    @Override
    public String getDescription() {
        return "CanUpgrade";
    }

    public Iceberg getIceberg() {
        return iceberg;
    }

    @Override
    public String toString() {
        return "CanUpgrade [toUpgrade=" + iceberg + "]";
    }
}
