package bots;

import java.util.*;
import java.util.stream.Collectors;

import penguin_game.*;

/**
 * betterIcberg
 * contain extra Function
 */
public class betterIceberg extends Iceberg {
    final int FACTOR = 10;

    /**
     *
     */
    public boolean underAttack = false; // need to check if necessary

    /**
     * constructor that coll to the super
     * 
     * 
     */
    public betterIceberg() {
        super();
    }

    /**
     * copt constructor
     * @param iceberg
     */
    public betterIceberg(Iceberg iceberg) {
        super();
        Objects.requireNonNull(iceberg);
        this.__canUpgradeObject = iceberg.__canUpgradeObject;
        this.isIcepital = iceberg.isIcepital;
        this.upgradeValue = iceberg.upgradeValue;
        this.upgradeCost = iceberg.upgradeCost;
        this.level = iceberg.level;
        this.upgradeLevelLimit = iceberg.upgradeLevelLimit;
        this.costFactor = iceberg.costFactor;
        this.penguinAmount = iceberg.penguinAmount;
        this.__penguinGroupMaxSpeed = iceberg.__penguinGroupMaxSpeed;
        this.penguinsPerTurn = iceberg.penguinsPerTurn;
    }

    /**
     * is the {@link betterIceberg} is in danger or not
     * 
     * @param enemy {@link Player}
     * @return {@code null} if there is no attack
     *         or the amount of penguins that will remain after the fight
     */
    public Integer isInDanger(Player enemy) {
        int amountPenguinOnTheWay = 0; // amount penguin on the way
        int amountPenguinAtArrival = this.penguinAmount; // amount the penguin at the capital at the arrival
        List<PenguinGroup> enemyPenguinGroups = new LinkedList<>(Arrays.asList(enemy.penguinGroups));
        for (PenguinGroup i : enemyPenguinGroups) {
            amountPenguinOnTheWay += i.penguinAmount;
            amountPenguinAtArrival += i.turnsTillArrival * this.penguinsPerTurn;
        }
        if (enemyPenguinGroups.size() != 0) {
            underAttack = true;
            return amountPenguinAtArrival - amountPenguinOnTheWay + this.FACTOR;
        }
        return null;
    }

    /**
     * Sends troops to help strengthen the defense, evenly distributed among all
     * outposts
     * 
     * @param enemy
     * @param amount of the penguin backup that required
     * @param list   all the {@link betterIceberg}
     * @return {@code true} if successful and there are enough troops and
     *         {@code false} if not
     */
    public boolean ProtectIceberg(Player enemy, int amount, List<betterIceberg> list) {
        List<betterIceberg> notInDanger = new ArrayList<betterIceberg>();
        for (betterIceberg element : list) {
            if (element.isInDanger(enemy) > 0) {
                notInDanger.add(element);
            }
        }
        int size = notInDanger.size();
        if (size == 0) {
            return false;
        }
        notInDanger = notInDanger
                .stream()
                .filter(i -> i.canSendPenguins(this, amount / size))
                .collect(Collectors.toList());
        for (betterIceberg Iceberg : notInDanger) {
            Iceberg.sendPenguins(this, amount / size);
        }
        return true;
    }
}