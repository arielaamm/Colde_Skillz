package bots.Calc;

import bots.Fact.AnalyzeOutput;
import penguin_game.Game;

public class MainCalc {

    public static CalcOutput getCalc(Game game, AnalyzeOutput analyzeOutput) {
        return new CalcOutput(game, analyzeOutput);
    }
}
