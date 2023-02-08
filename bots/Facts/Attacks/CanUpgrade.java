package bots.Facts.Attacks;

import bots.Facts.Attack;
import penguin_game.Iceberg;

// ?: why CanUpgrade is extends Attack ? What is the connection between them?
// ANSWER: hell I don't know, it is like an attack option. it is not alert or announcement
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
}
