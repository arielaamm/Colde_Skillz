package bots.Functions;

import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

public class IsAttack {
    public static boolean isEnemyAttacks(Iceberg iceberg, Game game) {
        for (PenguinGroup penguinGroup : game.getEnemyPenguinGroups()) {
            if (penguinGroup.destination == iceberg) {
                return true;
            }
        }
        return false;
    }
}
