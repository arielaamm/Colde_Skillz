package bots.Fact.Alerts;

import java.util.*;

import bots.Fact.FactObject.UnderAttackFact;
import bots.Fact.FactObject.Fact;
import penguin_game.Game;
import penguin_game.PenguinGroup;
import bots.MapPrediction;
import bots.Fact.PriorityEnum;;

public class UnderAttack extends AlertOperator {
    @Override
    public List<Fact> getAlerts(Game game) {
        List<Fact> facts = new LinkedList<Fact>();
        for (PenguinGroup penguinGroup : game.getEnemyPenguinGroups()) {
            if (penguinGroup.destination.owner == game.getMyself() &&
                    !(Arrays.asList(game.getMyIcepitalIcebergs())
                            .contains(penguinGroup.destination))) {
                if (MapPrediction.AmountAtIceberg(penguinGroup.turnsTillArrival, penguinGroup.destination, game) < 0) {
                    facts.add(new UnderAttackFact("UnderAttack", PriorityEnum.Important, penguinGroup));
                }
            }
        }
        return facts;
    }

}
