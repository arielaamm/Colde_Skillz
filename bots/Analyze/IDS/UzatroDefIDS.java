package bots.Analyze.IDS;

import bots.DataBases.Knowledge;
import bots.Facts.Alert;
import bots.Facts.Alerts.UzatroDefAlert;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

import java.util.ArrayList;
import java.util.List;
//TODO: CHECK THIS CODE []
public class UzatroDefIDS extends IDS{
    IDS ids;

    public UzatroDefIDS(IDS ids) {
        this.ids = ids;
    }

    @Override
    public List<Alert> getAlerts(Game game) {
        List<Alert> alerts = ids.getAlerts(game);
        Knowledge knowledge = Knowledge.getInstance();
        knowledge.updateNaturalWeTaking();
        //for each icebreg i attack i check if enemy attacks too
        for (Iceberg iceberg : knowledge.getNaturalWeTaking()) {
            List<PenguinGroup> attackingEnemy = new ArrayList<>();
            for (PenguinGroup penguinGroup : game.getEnemyPenguinGroups()) {
                if(penguinGroup.destination == iceberg) {
                    alerts.add(new UzatroDefAlert(iceberg, penguinGroup));
                    break;
                }
            }
        }
        return alerts;
    }

}
