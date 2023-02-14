package bots.Facts.Attacks;

import bots.Facts.Attack;
import penguin_game.Iceberg;

public class CanUpgrade extends Attack {
    private Iceberg toUpgrade;

    public CanUpgrade(Iceberg toUpgrade) {
        this.toUpgrade = toUpgrade;
    }

    @Override
    public String getDescription() {
        return "CanUpgrade";
    }

    public Iceberg getToUpgrade() {
        return toUpgrade;
    }

    @Override
    public String toString() {
        return "CanUpgrade [toUpgrade=" + toUpgrade + "]";
    }
}
