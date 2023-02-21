package bots.Fact.Alerts;

import java.util.*;

import bots.MapPrediction;
import bots.Fact.PriorityEnum;
import bots.Fact.FactObject.UnderAttackFact;
import bots.Fact.FactObject.Fact;
import penguin_game.Game;
import penguin_game.PenguinGroup;

public class UnderAttackOnIcepital extends AlertOperator {
    @Override
    public List<Fact> getAlerts(Game game) {
        List<Fact> facts = new LinkedList<Fact>();
        for (PenguinGroup penguinGroup : game.getEnemyPenguinGroups()) {
            if (Arrays.asList(game.getMyIcepitalIcebergs()).contains(penguinGroup.destination)) {
                if (MapPrediction.AmountAtIceberg(penguinGroup.turnsTillArrival, penguinGroup.destination,
                        game) < 0) {
                    facts.add(new UnderAttackFact("UnderAttackOnIcepital", PriorityEnum.SuperImportant, penguinGroup));
                }
            }
        }
        return facts;
    }

}
