package bots.Facts.Alerts;

import bots.Facts.Alert;
import penguin_game.Iceberg;
import penguin_game.PenguinGroup;

//TODO: CHECK THIS CODE []
public class UzatroDefAlert extends Alert {
    private Iceberg underAttack;
    private PenguinGroup attacker;

    public UzatroDefAlert(Iceberg underAttack, PenguinGroup attacker) {
        this.underAttack = underAttack;
        this.attacker = attacker;
    }

    @Override
    public String getDescription() {
        return "UzatroDefAlert";
    }

    public Iceberg getUnderAttack() {
        return underAttack;
    }

    public PenguinGroup getAttacker() {
        return attacker;
    }

    @Override
    public String toString() {
        return "UzatroDefAlert [target= " + underAttack + " attacker= " + attacker + "]";
    }
}
