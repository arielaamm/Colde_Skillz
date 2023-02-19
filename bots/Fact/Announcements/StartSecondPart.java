package bots.Fact.Announcements;

import java.util.*;

import bots.Fact.FactObject.Fact;
import penguin_game.Game;

public class StartSecondPart extends AnnouncementOperator {
    @Override
    public List<Fact> getAnnouncements(Game game) {
        List<Fact> facts = new LinkedList<Fact>();

        //TODO: When does this message pop up? 
        /*
        facts.add(new MessageFact("StartSecondPart",PriorityEnum.Unimportant));
        */
        return facts;

    }
}
