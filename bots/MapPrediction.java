package bots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bots.Fact.Alerts.UnderAttack;
import bots.Fact.Alerts.UnderAttackOnIcepital;
import bots.Fact.FactObject.Fact;
import bots.Fact.FactObject.UnderAttackFact;
import penguin_game.Game;
import penguin_game.IceBuilding;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

public class MapPrediction {

    public static PenguinGroup[] getSortedByTurnsTillArrival(PenguinGroup[] groups) {
        PenguinGroup[] penguinGroup = groups;
        Arrays.sort(penguinGroup, new Comparator<PenguinGroup>() {
            public int compare(PenguinGroup p1, PenguinGroup p2) {
                return Integer.compare(p1.turnsTillArrival, p2.turnsTillArrival);
            }
        });
        return penguinGroup;
    }

    public static int AmountAtIceberg(int turnAhead, IceBuilding iceberg, Game game) {
        if (!(iceberg instanceof Iceberg)) {
            return 0;
        }
        PenguinGroup[] allPenguinGroups = getSortedByTurnsTillArrival(game.getAllPenguinGroups());
        int amout = iceberg.penguinAmount;
        boolean isReset = false;
        for (int i = 0; i < turnAhead; i++) {
            for (PenguinGroup penguinGroup : allPenguinGroups) {
                if (penguinGroup.destination == iceberg) {
                    if (penguinGroup.destination.owner == game.getMyself()) {
                        amout += penguinGroup.penguinAmount;
                    } else {
                        amout -= penguinGroup.penguinAmount;
                    }
                }
                if (amout <= 0) {
                    isReset = true;
                }
            }
            if (amout > 0 && !isReset) {
                amout += ((Iceberg) iceberg).penguinsPerTurn;
            }
            if (amout > 0 && isReset) {
                amout += 1;
            } else {
                amout -= 1;
            }
        }

        return amout;
    }

    public static int getAmountFreePenguin(Iceberg iceberg, Game game) {
        List<Fact> UnderAttack;
        Fact faresAttack;
        if (List.of(game.getMyIcepitalIcebergs()).contains(iceberg)) {
            UnderAttack = new UnderAttackOnIcepital().getAlerts(game);
        } else {
            UnderAttack = new UnderAttack().getAlerts(game);
        }
        if (UnderAttack.isEmpty()) {
            return iceberg.penguinAmount;
        }
        faresAttack = UnderAttack.get(0);
        int turns = ((UnderAttackFact) faresAttack).getPenguinGroup().turnsTillArrival;

        for (Fact fact : UnderAttack) {
            if (((UnderAttackFact) fact).getPenguinGroup().turnsTillArrival > ((UnderAttackFact) faresAttack)
                    .getPenguinGroup().turnsTillArrival) {
                faresAttack = fact;
                turns = ((UnderAttackFact) fact).getPenguinGroup().turnsTillArrival;
            }
        }
        return AmountAtIceberg(turns, ((UnderAttackFact) faresAttack).getPenguinGroup().destination, game);
    }
}

