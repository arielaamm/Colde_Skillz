package bots.Facts.Attacks;

import penguin_game.Iceberg;

public class CanOverCome extends CanAttack{

    public CanOverCome(Iceberg target) {
        super(target);
    }

    @Override
    public String getDescription() {
        return "canOverCome";
    }

    @Override
    public String toString() {
        return "CanOverCome [target=" + getTarget() + "]";
    }
}
