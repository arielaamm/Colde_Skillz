package bots.Analyze.AnnouncmentShouter;

import bots.Facts.Announcement;
import penguin_game.Game;

import java.util.ArrayList;
import java.util.List;

public class DefultShouter extends AnnouncementShouterAbs{
    @Override
    public List<Announcement> shout(Game game) {
        return new ArrayList<>();
    }
}
