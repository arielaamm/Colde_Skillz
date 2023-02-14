package bots.Fact.FactObject;

import penguin_game.Iceberg;

public interface Fact {
    public int getPriority();
    public Iceberg getSource();
    public String getDescription();
}
