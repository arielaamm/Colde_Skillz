package bots.Executer;

import penguin_game.Iceberg;

public class UpgradeIcebergDecision implements Executable {
    private final Iceberg toUpgrade;

    public UpgradeIcebergDecision(Iceberg toUpgrade) {
        this.toUpgrade = toUpgrade;
    }

    @Override
    public void execute() {
        toUpgrade.upgrade();

    }

    @Override
    public String toString() {
        return "UpgradeIcebergDecision [toUpgrade=" + toUpgrade + "]\n";
    }

    public Iceberg getToUpgrade() {
        return toUpgrade;
    }
}
