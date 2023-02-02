package bots.Analyze.Attack;

import bots.Facts.Attack;
import penguin_game.Game;
import penguin_game.Iceberg;

import java.util.List;

public class CanAttack extends AttackOption{
    AttackOption attackOption;
    public CanAttack(AttackOption attackOption1) {
        attackOption = attackOption1;
    }



    @Override
    public List<Attack> getAlerts(Game game) {
        List<Attack> toRet = attackOption.getAlerts(game); // get prev alerts
        Iceberg closest = game.getEnemyIcepitalIcebergs()[0];
        double distance = 99999999;
        //culc the closest iceberg to put as targets
        for (Iceberg target : game.getAllIcebergs()) {
            if (target.owner == game.__me) {
                continue;
                //want to run on all other targets
            }
            //want to find the natural iceberg to attack so to avrage from all my icebergs
            double newDistance = 0;
            for (Iceberg myIce : game.getMyIcebergs()) {
                newDistance+= myIce.getTurnsTillArrival(target);
            }
            if (newDistance / game.getMyIcebergs().length < distance) {
                //found closer iceberg to target
                distance = newDistance / game.getMyIcebergs().length;
                closest = target;
            }
        }
        toRet.add(new bots.Facts.Attacks.CanAttack(closest));
        return toRet;
    }

}
