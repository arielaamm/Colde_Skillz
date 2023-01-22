package bots;

import java.util.*;
import penguin_game.*;

public class demoBot implements SkillzBot {
    final int FACTOR = 10;

    /**
     * betterIcberg
     * contain extra Function
     */
    public class betterIceberg extends Iceberg {

        public boolean underAttack = false; // need to check if necessary

        /**
         * constructor that coll to the super
         */
        public betterIceberg() {
            super();
        }

        /**
         * cast from {@link Iceberg} to {@link betterIceberg}
         * 
         * @param icebergs[]
         * @return List<betterIceberg>
         */
        public static List<betterIceberg> cast(Iceberg[] icebergs) {
            List<betterIceberg> castIcebergs = new LinkedList<betterIceberg>();
            for (Iceberg iceberg : icebergs) {
                castIcebergs.add(((betterIceberg) iceberg));
            }
            return castIcebergs;

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
                return amountPenguinAtArrival - amountPenguinOnTheWay + FACTOR;
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
            notInDanger = notInDanger.stream().filter(i -> i.canSendPenguins(this, amount / size)).toList();
            for (betterIceberg Iceberg : notInDanger) {
                Iceberg.sendPenguins(this, amount / size);
            }
            return true;
        }
    }

    public void doTurn(Game game) {
        final Player enemy = game.getEnemy();
        final Player my = game.getMyself();
        betterIceberg myCapital = (betterIceberg.cast(my.icebergs))
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
            List<betterIceberg> temp = betterIceberg.cast(my.icebergs)
                    .stream()
                    .filter(i -> !(i.isIcepital))
                    .toList();
            myCapital.ProtectIceberg(enemy, penguinLeft + FACTOR, temp);
        }

        // שלב הבא לעשות מיון בין כל שאר בתים ומי שהכי קטן לבדוק לו את ההתקפות
    }
}
