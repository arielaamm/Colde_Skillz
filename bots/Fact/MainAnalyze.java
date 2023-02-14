package bots.Fact;

import java.util.LinkedList;
import java.util.List;

import bots.Fact.Alerts.AlertOperator;
import bots.Fact.Announcements.AnnouncementOperator;
import bots.Fact.Attacks.AttactOperator;
import bots.Fact.FactObject.Fact;
import penguin_game.Game;

public class MainAnalyze {
    public static List<Fact> getFindings(Game game) {
        List<Fact> facts = new AlertOperator(game).getAlerts();
        facts.addAll(new AnnouncementOperator(game).getAnnouncements());
        facts.addAll(new AttactOperator(game).getAttacts());
        return facts;

    }
}
