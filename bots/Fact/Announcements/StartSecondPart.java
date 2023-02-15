package bots.Fact.Announcements;

import java.util.*;

import bots.Fact.PriorityEnum;
import bots.Fact.FactObject.Fact;
import bots.Fact.FactObject.MessageFact;
import penguin_game.Game;

public class StartSecondPart extends AnnouncementOperator {
    @Override
    public List<Fact> getAnnouncements(Game game) {
        List<Fact> facts = new LinkedList<Fact>();
        facts.add(new MessageFact("StartSecondPart",PriorityEnum.Unimportant));
        return facts;
    }
}
