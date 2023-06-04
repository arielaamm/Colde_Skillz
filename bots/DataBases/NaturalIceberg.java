package bots.DataBases;
import bots.DataBases.Knowledge;
import penguin_game.Game;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;
import penguin_game.Player;

import java.util.ArrayList;
import java.util.List;
//TODO: CHECK THIS CODE []
public class NaturalIceberg {
    private List<Player> owners = new ArrayList<>();
    private List<Integer> numPengs = new ArrayList<>();
    private int lastTurnInteresting = 0;
    public NaturalIceberg(Iceberg iceberg) {
        Game game = Knowledge.getGame();
        //build the lists for the future and last turn arrive
        List<PenguinGroup> getInPengs = new ArrayList<>();
        for (PenguinGroup penguinGroup : game.getAllPenguinGroups()) {
            if (penguinGroup.destination == iceberg) {
                if (penguinGroup.isSiegeGroup) {
                    continue;
                }
                getInPengs.add(penguinGroup);
                if(penguinGroup.turnsTillArrival > lastTurnInteresting) {
                    lastTurnInteresting = penguinGroup.turnsTillArrival;
                }
            }
        }
        //assume that not two groups get in the same time
        owners.add(iceberg.owner);
        numPengs.add(iceberg.penguinAmount);
        for (int i = 1; i < lastTurnInteresting; i++) {
            int lastTurnPengs = numPengs.get(i-1);
            Player lastTurnOwner = owners.get(i-1);
            if(lastTurnOwner != game.getNeutral()) {
                lastTurnPengs++;
            }
            for (PenguinGroup penguinGroup : getInPengs) {
                if(penguinGroup.turnsTillArrival == i) {
                    if(penguinGroup.owner == lastTurnOwner) {
                        lastTurnPengs += penguinGroup.penguinAmount;
                    } else {
                        lastTurnPengs -= penguinGroup.penguinAmount;
                        if (lastTurnPengs < 0) {
                            lastTurnPengs = -lastTurnPengs;
                            lastTurnOwner = penguinGroup.owner;
                        }
                    }
                }
            }
            numPengs.add(lastTurnPengs);
            owners.add((lastTurnOwner));
        }

    }

    public List<Integer> getNumPengs() {
        return numPengs;
    }

    public int getLastTurnInteresting() {
        return lastTurnInteresting;
    }

    public List<Player> getOwners() {
        return owners;
    }

    @Override
    public String toString() {
        return "NaturalIceberg[" +
                "owners=" + owners +
                ", numPengs=" + numPengs +
                ", lastTurnInteresting=" + lastTurnInteresting +
                ']';
    }
}
