package bots.Analyze.Attack;

import bots.Facts.Attack;
import penguin_game.Game;

import java.util.ArrayList;
import java.util.List;

public class DefaultAttackAnalyze extends AttackOption{
    @Override
    public List<Attack> getAlerts(Game game) {
        return new ArrayList<>();
    }
}
