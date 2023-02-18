package bots.Fact.Attacks;

import java.util.*;

import bots.MapPrediction;
import bots.Fact.PriorityEnum;
import bots.Fact.FactObject.CanAttackFact;
import bots.Fact.FactObject.Fact;
import penguin_game.Game;
import penguin_game.Iceberg;

public class CanAttack extends AttactOperator {
    @Override
    public List<Fact> getAttacts(Game game) {
        List<Fact> facts = new LinkedList<Fact>();
        // if we are on part 1
        for (Iceberg myIceberg : game.getMyIcebergs()) {
            for (Iceberg neutralIceberg : game.getNeutralIcebergs()) {
                int turn = myIceberg.getTurnsTillArrival(neutralIceberg);
                int freePenguinAmount = MapPrediction.getAmountFreePenguin(myIceberg, game);
                if (freePenguinAmount <= 0) {
                    continue;
                }
                int amountToCapture = MapPrediction.AmountAtIceberg(turn, neutralIceberg, game) + 2;
                if (amountToCapture < myIceberg.penguinAmount) {
                    facts.add(new CanAttackFact(neutralIceberg, myIceberg, "CanAttack", PriorityEnum.Important,freePenguinAmount, amountToCapture));
                }
            }
        }
        return facts;
    }

}
