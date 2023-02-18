package bots.Fact.Announcements;

import java.util.LinkedList;
import java.util.List;

import bots.Fact.FactObject.Fact;
import penguin_game.Game;

public class AnnouncementOperator {
    List<Fact> facts = new LinkedList<Fact>();

    public AnnouncementOperator() {
    }

    public AnnouncementOperator(Game game) {
        facts.addAll(new CanUpgrade().getAnnouncements(game));
        facts.addAll(new StartSecondPart().getAnnouncements(game));
        facts.addAll(new StartThiredPart().getAnnouncements(game));
    }

    public List<Fact> getAnnouncements(Game game) {
        return facts;
    }

    public List<Fact> getAnnouncements() {
        return facts;
    }

    @Override
    public String toString() {
        return "AnnouncementOperator [facts=" + facts + "]";
    }


}
