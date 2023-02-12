package bots.Analyze.AnnouncmentShouter;

import bots.DataBases.Knowledge;
import bots.Facts.Announcement;
import bots.Facts.Announcements.StartSecondPart;
import penguin_game.Game;

import java.util.List;

public class StartPartTwo extends AnnouncementShouterAbs{
    private AnnouncementShouterAbs announcementShouter;

    public StartPartTwo(AnnouncementShouterAbs announcementShouter) {
        this.announcementShouter = announcementShouter;
    }

    @Override
    public List<Announcement> shout(Game game) {
        List<Announcement> announcements = announcementShouter.shout(game);
        Knowledge knowledge = Knowledge.getInstance();
        if (knowledge.getPartInGameNumber() != 1) {
            return announcements;
        }
        if (game.getNeutralIcebergs().length <= 1) {
            Announcement announcement = new StartSecondPart();
            announcements.add(announcement);
        } else {

        }
        return announcements;
    }
}
