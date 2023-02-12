package bots.Analyze.Attack;

import bots.Facts.Attack;
import penguin_game.Game;
import penguin_game.Iceberg;

import java.util.List;

/**
 * here add all the icebergs can upgrade
 */
public class CanUpgradeAnalyze extends AttackOption {
    AttackOption attackOption;

    public CanUpgradeAnalyze(AttackOption attackOption1) {
        attackOption = attackOption1;
    }

    @Override
    public List<Attack> getAlerts(Game game) {
        List<Attack> ret = attackOption.getAlerts(game);
        for (Iceberg ice : game.getMyIcebergs()) {
            if (ice.penguinAmount >= ice.upgradeCost) {
                ret.add(new bots.Facts.Attacks.CanUpgrade(ice));
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return "CanUpgradeAnalyze [attackOption=" + attackOption + "]\n";
    }

}
