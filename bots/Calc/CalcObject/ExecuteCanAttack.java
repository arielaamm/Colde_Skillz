package bots.Calc.CalcObject;

import penguin_game.Iceberg;

public class ExecuteCanAttack {
    public String info;
    public int amount;
    public int recommendedSpeed;
    public Iceberg source;
    public Iceberg target;

    public ExecuteCanAttack(String info, int amount, int recommendedSpeed, Iceberg source, Iceberg target) {
        this.info = info;
        this.amount = amount;
        this.recommendedSpeed = recommendedSpeed;
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "ExecuteCanAttack [info=" + info + ", amount=" + amount + ", recommendedSpeed=" + recommendedSpeed
                + ", source=" + source + ", target=" + target + "]";
    }

}
