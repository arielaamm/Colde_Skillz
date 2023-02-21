package bots.Calc.CalcObject;

import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

public class ExecuteUnderAttack {
    public String info;
    public PenguinGroup penguinGroup;
    public Iceberg source;
    public Iceberg target;

    public ExecuteUnderAttack(String info, PenguinGroup penguinGroup, Iceberg source, Iceberg target) {
        this.info = info;
        this.penguinGroup = penguinGroup;
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "ExecuteUnderAttack [info=" + info + ", penguinGroup=" + penguinGroup + ", source=" + source
                + ", target=" + target + "]";
    }

}
