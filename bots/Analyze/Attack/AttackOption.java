package bots.Analyze.Attack;

import bots.Facts.Attack;
import penguin_game.Game;

import java.util.List;

/**
 * 
 */
public abstract class AttackOption {
    public abstract List<Attack> getAlerts(Game game);
}
