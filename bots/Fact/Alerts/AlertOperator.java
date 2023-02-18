package bots.Fact.Alerts;

import java.util.LinkedList;
import java.util.List;

import bots.Fact.FactObject.Fact;
import penguin_game.Game;

public class AlertOperator {
    List<Fact> facts = new LinkedList<Fact>();

    public AlertOperator() {
    }

    public AlertOperator(Game game) {
        facts.addAll(new UnderAttack().getAlerts(game));
        facts.addAll(new UnderAttackOnIcepital().getAlerts(game));
    }
    
    public List<Fact> getAlerts(Game game) {
        return facts;
    }
    
    public List<Fact> getAlerts() {
        return facts;
    }

    @Override
    public String toString() {
        return "AlertOperator [facts=" + facts + "]";
    }
}
