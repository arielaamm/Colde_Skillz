package bots;

import bots.Calc.*;
import bots.Fact.*;
import penguin_game.*;

public class MyBot implements SkillzBot {
    //check
    public void doTurn(Game game) {
        AnalyzeOutput analyzeOutput = MainAnalyze.getFindings(game);
        game.debug(analyzeOutput);
        CalcOutput calcOutput = MainCalc.getCalc(game,analyzeOutput);
        game.debug(calcOutput);
        
    }
}
