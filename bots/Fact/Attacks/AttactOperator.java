package bots.Fact.Attacks;

import java.util.LinkedList;
import java.util.List;

import bots.Fact.FactObject.Fact;
import penguin_game.Game;

public class AttactOperator {
    List<Fact> facts = new LinkedList<Fact>();

    public AttactOperator() {
    }

    public AttactOperator(Game game) {
        facts.addAll(new CanAttack().getAttacts(game));
        facts.addAll(new CanOverCome().getAttacts(game));
    }

    public List<Fact> getAttacts(Game game) {
        return facts;
    }
    
    public List<Fact> getAttacts() {
        return facts;
    }

    @Override
    public String toString() {
        return "AttactOperator [facts=" + facts + "]";
    }
}
