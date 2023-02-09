package bots.Facts.Alerts;
import bots.Facts.Alert;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.List;

/**
 * Alert that the {@link Iceberg} under attack
 */
public class UnderAttackAlert extends Alert {
    private Iceberg target;
    private List<Iceberg> sources;
    private List<PenguinGroup> attackers;

    public UnderAttackAlert(Iceberg target, List<Iceberg> sources, List<PenguinGroup> attackers) {
        this.target = target;
        this.sources = sources;
        this.attackers = attackers;
    }

    
    /** 
     * @return target - {@link Iceberg}
     */
    public Iceberg getTarget() {
        return target;
    }

    @Override
    public String getDescription() {
        return "UnderAttack";
    }

    /**
     * @return list of the attackers - {@link PenguinGroup}
     */
    public List<PenguinGroup> getAttackers() {
        return attackers;
    }

    /**
     * @return list of the sources of the attackers - {@link Iceberg}
     */
    public List<Iceberg> getSources() {
        return sources;
    }


    @Override
    public String toString() {
        return "UnderAttackAlert [target=" + target + ", sources=" + sources + ", attackers=" + attackers + "]";
    }


}
