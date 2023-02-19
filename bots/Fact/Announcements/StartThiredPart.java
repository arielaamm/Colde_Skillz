package bots.Fact.Announcements;

import java.util.*;

import bots.Fact.PriorityEnum;
import bots.Fact.FactObject.Fact;
import bots.Fact.FactObject.MessageFact;
import penguin_game.Game;

public class StartThiredPart extends AnnouncementOperator {
    @Override
    public List<Fact> getAnnouncements(Game game) {
        List<Fact> facts = new LinkedList<Fact>();

        //TODO: When does this message pop up? 
        /*
        facts.add(new MessageFact("StartThiredPart",PriorityEnum.Unimportant));
        */
        return facts;
    }

}
