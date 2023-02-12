package bots.Analyze;

import bots.Analyze.AnnouncmentShouter.AnnouncementShouterAbs;
import bots.Analyze.AnnouncmentShouter.DefultShouter;
import bots.Analyze.AnnouncmentShouter.StartPartTwo;
import bots.Analyze.Attack.AttackOption;
import bots.Analyze.Attack.CanAttackAnalyze;
import bots.Analyze.Attack.CanUpgradeAnalyze;
import bots.Analyze.Attack.DefaultAttackAnalyze;
import bots.Analyze.IDS.DefultIDS;
import bots.Analyze.IDS.IDS;
import bots.Analyze.IDS.UnderAttackIDS;
import bots.Facts.AnalyzeOutput;
import penguin_game.Game;

import java.util.ArrayList;

/**
 * Analyze the data and return {@link AnalyzeOutput}
 */
public abstract class MainAnalyze {
    /**
     * 
     * @param game - {@link Game}
     * @return analyzed data - {@link AnalyzeOutput}
     */
    public static AnalyzeOutput getFacts(Game game) {
        IDS ids = new UnderAttackIDS(new DefultIDS());
        AttackOption attackOption = new CanUpgradeAnalyze(new CanAttackAnalyze(new DefaultAttackAnalyze()));
        AnnouncementShouterAbs announcementShouterAbs = new StartPartTwo(new DefultShouter());
        return new AnalyzeOutput(ids.getAlerts(game), 
                                attackOption.getAlerts(game), announcementShouterAbs.shout(game));
    }
}
