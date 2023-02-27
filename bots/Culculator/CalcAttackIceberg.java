package bots.Culculator;

import penguin_game.Iceberg;

public class CalcAttackIceberg {

    private Iceberg myIceberg;
    private int canDonate;
    private int speed;
    public CalcAttackIceberg(Iceberg first, int canDonate, int speed) {
        this.myIceberg = first;
        this.canDonate = canDonate;
        this.speed = speed;
    }

    public Iceberg getMyIceberg() {
        return myIceberg;
    }
    public int getCanDonate() {
        return canDonate;
    }
    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "CalcAttackIceberg [first=" + myIceberg + ", canDonate=" + canDonate + ", speed=" + speed + "]";
    }
}
