package bots.Fact.Announcements;

import java.util.*;

import bots.Fact.PriorityEnum;
import bots.Fact.FactObject.CanUpgradeMessage;
import bots.Fact.FactObject.Fact;
import penguin_game.Game;
import penguin_game.Iceberg;

public class CanUpgrade extends AnnouncementOperator {
    @Override
    public List<Fact> getAnnouncements(Game game) {
        List<Fact> facts = new LinkedList<Fact>();
        for (Iceberg iceberg : game.getMyIcebergs()) {
            if(iceberg.upgradeCost + 2 <= iceberg.penguinAmount)
            {
                facts.add(new CanUpgradeMessage("CanUpgrade", PriorityEnum.LessImportant, iceberg));
            }
        }
        return facts;
    }
}
