package bots.Calc;

import bots.Fact.AnalyzeOutput;
import bots.Fact.FactObject.Fact;
import penguin_game.Game;

public class Calculator {
    public static CalcOutput calc(Game game, AnalyzeOutput analyzeOutput)
    {
        CalcOutput calcOutput = new CalcOutput();
        for (Fact fact : analyzeOutput.announcements) {
            if (fact.getDescription() == "StartSecondPart") {
                calcOutput = SecondPart(game, analyzeOutput);
            }
            if (fact.getDescription() == "StartThiredPart") {
                calcOutput = ThiredPart(game, analyzeOutput);
            } else {
                calcOutput = firstPart(game, analyzeOutput);
            }
        }
        return calcOutput;
    }
    static CalcOutput firstPart(Game game, AnalyzeOutput analyzeOutput){

    }

    static CalcOutput SecondPart(Game game, AnalyzeOutput analyzeOutput){

    }

    static CalcOutput ThiredPart(Game game, AnalyzeOutput analyzeOutput){

    }
}
