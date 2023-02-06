package bots.Exeuter;

import penguin_game.Iceberg;

public class UpgradeIceberg implements Executable{
    private Iceberg toUpgrade;

    public UpgradeIceberg(Iceberg toUpgrade) {
        this.toUpgrade = toUpgrade;
    }

    @Override
    public void execute() {
           toUpgrade.upgrade();

    }
}
