package bots.Analyze.IDS;

import bots.Facts.Alert;
import penguin_game.Game;

import java.util.List;

public abstract class IDS {
    public abstract List<Alert> getAlerts(Game game);
}
