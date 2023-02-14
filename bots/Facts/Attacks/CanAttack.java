package bots.Facts.Attacks;

import bots.Facts.Attack;
import penguin_game.Iceberg;

public class CanAttack extends Attack {
    private Iceberg target;

    public CanAttack(Iceberg target) {
        this.target = target;
    }

    @Override
    public String getDescription() {
        return "canAttack";
    }

    public Iceberg getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "CanAttack [target=" + target + "]";
    }
}
