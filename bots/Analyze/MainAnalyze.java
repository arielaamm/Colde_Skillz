package bots.Analyze;

import bots.Analyze.Attack.AttackOption;
import bots.Analyze.Attack.CanAttack;
import bots.Analyze.Attack.DefultAttack;
import bots.Analyze.IDS.DefultIDS;
import bots.Analyze.IDS.IDS;
import bots.Analyze.IDS.UnderAttackIDS;
import bots.Facts.AnalyzeOutput;
import bots.Facts.Attack;
import penguin_game.Game;

import java.util.ArrayList;
import java.util.List;

public class MainAnalyze {
    public AnalyzeOutput getFacts(Game game) {
        IDS ids = new UnderAttackIDS(new DefultIDS());
        AttackOption attackOption = new CanAttack(new DefultAttack());
        return  new AnalyzeOutput(ids.getAlerts(game), attackOption.getAlerts(game), new ArrayList<>());
    }
}
