package bots.Fact;

import bots.Fact.Alerts.AlertOperator;
import bots.Fact.Announcements.AnnouncementOperator;
import bots.Fact.Attacks.AttactOperator;
import penguin_game.Game;

public class MainAnalyze {
    public static AnalyzeOutput getFindings(Game game) {
        return new AnalyzeOutput(
            new AlertOperator(game).getAlerts(),
            new AnnouncementOperator(game).getAnnouncements(),
            new AttactOperator(game).getAttacts());

    }
}
