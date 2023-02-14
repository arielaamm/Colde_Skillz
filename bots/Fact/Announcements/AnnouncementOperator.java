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
        facts.addAll(new CanUpGrade().getAnnouncements(game));
        facts.addAll(new StartSecondPart().getAnnouncements(game));
        facts.addAll(new StartThiredPart().getAnnouncements(game));
    }

    boolean isHandle;

    public boolean isHandle() {
        return isHandle;
    }

    public void setHandle(boolean isHandle) {
        isHandle = this.isHandle;
    }

    public List<Fact> getAnnouncements(Game game) {
        return facts;
    }

    public List<Fact> getAnnouncements() {
        return facts;
    }

    @Override
    public String toString() {
        return "AnnouncementOperator [facts=" + facts + ", isHandle=" + isHandle + "]";
    }
}
