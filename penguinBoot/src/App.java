package bots;
import penguin_game.*;

import java.util.HashMap;
import java.util.Map;

public class MyBot implements SkillzBot {

    public void doTurn(Game game){
        Player enemy = game.getEnemy();
        Map<Iceberg, Integer> underAttack = new HashMap<>();
        for (PenguinGroup pg : enemy.penguinGroups) {
            int allreadyOnTheWay = 0;
            if( underAttack.get(pg.destination) != null){
                allreadyOnTheWay = underAttack.get(pg.destination);
            }
            if (pg.turnsTillArrival > 2) {
                allreadyOnTheWay -= 1;
            }
            underAttack.put(pg.destination, pg.penguinAmount + allreadyOnTheWay);
        }
        int number = 0;
        for (Iceberg ice : game.getMyIcebergs()) {
            if (underAttack.get(ice) != null){
                if (ice.penguinAmount - underAttack.get(ice) >= 1) {
                    number += 2;
                    ice.sendPenguins(game.getEnemy().icebergs[0], ice.penguinAmount - underAttack.get(ice));
                }}
            else {
                number +=2;
                if (number == 8) {
                    number = 0;
                }
                if (game.getAllIcebergs()[number] == ice) {
                    ice.sendPenguins(game.getAllIcebergs()[0], ice.penguinAmount);
                }
                ice.sendPenguins(game.getAllIcebergs()[number], ice.penguinAmount);
            }
        }

    }
}
