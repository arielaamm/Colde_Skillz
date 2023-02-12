package bots.Facts.Attacks;

import bots.Facts.Attack;
import penguin_game.Iceberg;

//NOTE: it is extends Attack because it can improve our attack
//ANSWER: agried
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
