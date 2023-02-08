package bots.Executer;

import penguin_game.Iceberg;

public class UpgradeIcebergDecision implements Executable{
    private Iceberg toUpgrade;

    public UpgradeIcebergDecision(Iceberg toUpgrade) {
        this.toUpgrade = toUpgrade;
    }

    @Override
    public void execute() {
        toUpgrade.upgrade();

    }
}
