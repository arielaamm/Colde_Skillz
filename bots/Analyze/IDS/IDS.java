package bots.Analyze.IDS;

import bots.Facts.Alert;
import penguin_game.Game;

import java.util.List;

/**
 * {@code abstract class} <pre/>
 * system ____ //[ ]: complite the definition
 */

public abstract class IDS {
    /**
     * @param game
     * @return list of {@link Alert}
     */
    public abstract List<Alert> getAlerts(Game game);
}
