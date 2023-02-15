package bots;

import java.util.Arrays;
import java.util.Comparator;

import penguin_game.Game;
import penguin_game.IceBuilding;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

public class MapPrediction {
    public static int AmountAtIceberg(int turnAhead,IceBuilding iceberg,Game game)
    {
        if(!(iceberg instanceof Iceberg))
        {
            return 0;
        }
        PenguinGroup[] allPenguinGroups = game.getAllPenguinGroups();
        int amout = iceberg.penguinAmount;
        boolean isReset = false;
        Arrays.sort(allPenguinGroups, new Comparator<PenguinGroup>() {
            public int compare(PenguinGroup p1, PenguinGroup p2) {
                return Integer.compare(p1.turnsTillArrival, p2.turnsTillArrival);
            }
        });
        for (int i = 0; i < turnAhead; i++) {
            for (PenguinGroup penguinGroup : allPenguinGroups) {
                if (penguinGroup.destination == iceberg) {
                    if(penguinGroup.destination.owner == game.getMyself())
                    {
                        amout += penguinGroup.penguinAmount;
                    }
                    else
                    {
                        amout -= penguinGroup.penguinAmount;
                    }
                }
                if (amout <= 0) {
                    isReset = true;
                }
            }
            if (amout > 0 && !isReset) {
                amout += ((Iceberg)iceberg).penguinsPerTurn;
            }
            if (amout > 0 && isReset) {
                amout += 1;
            } else {
                amout -= 1;
            }
        }

        return amout;
    }
}
