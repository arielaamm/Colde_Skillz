package bots;

import java.util.*;
import java.util.stream.Collectors;

import penguin_game.*;

public class demoBot implements SkillzBot {
    final int FACTOR = 10;

    public void doTurn(Game game) {
        final Player enemy = game.getEnemy();
        final Player my = game.getMyself();
        betterIceberg myCapital = (betterIcebergTool.cast(my.icebergs))
                .stream()
                .filter(i -> i.isIcepital)
                .findFirst()
                .get();
        int penguinLeft = 1;
        if (myCapital.isInDanger(enemy) != null) {
            penguinLeft = myCapital.isInDanger(enemy);
        }
        // First case: the Capital is attaced
        if (penguinLeft <= 0) {
            List<betterIceberg> temp = betterIcebergTool.cast(my.icebergs)
                    .stream()
                    .filter(i -> !(i.isIcepital))
                    .collect(Collectors.toList());
            myCapital.ProtectIceberg(enemy, penguinLeft + FACTOR, temp);
        }

        // second case: Capture the closest Iceberg
        Iceberg[] arr =game.getAllIcebergs();
        for (int j =0 ; j < 3;j++) {
            Iceberg IcebergDonor = betterIcebergTool.getTheMostFertileIceberg(game.getNeutralIcebergs());
            if (IcebergDonor.canSendPenguins(arr[j], IcebergDonor.penguinAmount / 2)) {
                IcebergDonor.sendPenguins(arr[j], IcebergDonor.penguinAmount / 2);
            }
        }
        // שלב הבא לעשות מיון בין כל שאר בתים ומי שהכי קטן לבדוק לו את ההתקפות
        /*הקוד לא בדיוק עובד 
         * זה הבעיה שהם מחזירים לי 
         * שקריאה בשורה 37 - 36 לא נכונה ולא מתבצעת כמו שצריך
         */
    }
}
