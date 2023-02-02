package bots.Analyze.IDS;

import bots.Facts.Alerts.UnderAttackAlert;
import bots.Facts.Alert;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.ArrayList;
import java.util.List;

public class UnderAttackIDS extends IDS{
    IDS ids;
    public UnderAttackIDS(IDS ids1) {
        ids = ids1;
    }

    @Override
    public List<Alert> getAlerts(Game game) {
        List<Alert> toRet = ids.getAlerts(game); //get prev alerts
        for (Iceberg myIce : game.getMyIcebergs()) {
            //for each iceberg of mine will check if under attack
            List<Iceberg> sources = new ArrayList<>();
            List<PenguinGroup> penguinGroupsAttackers = new ArrayList<>();
            for (PenguinGroup attackers : game.getEnemyPenguinGroups()) {
                //check the enemy ping groups if they attack the iceberg
                if (attackers.destination == myIce) {
                    if (!sources.contains(attackers.source)) {
                        sources.add(attackers.source);
                    }
                    penguinGroupsAttackers.add(attackers);
                    //create the Alert and add it to the list
                    toRet.add(new UnderAttackAlert(myIce, sources, penguinGroupsAttackers));
                }
            }
        }
        return toRet;
    }

}
