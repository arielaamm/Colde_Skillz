package bots.Analyze.IDS;

import bots.Facts.Alert;
import penguin_game.Game;

import java.util.ArrayList;
import java.util.List;

public class DefultIDS extends IDS{

    @Override
    public List<Alert> getAlerts(Game game) {
        return new ArrayList<Alert>();
    }
}
