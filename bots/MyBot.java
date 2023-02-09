package bots;

import bots.Analyze.MainAnalyze;
import bots.Culculator.Calculator;
import bots.Executer.ExecuteMain;
import bots.Facts.AnalyzeOutput;
import penguin_game.*;

public class MyBot implements SkillzBot {

    //[ ]: complite this func, and if so mark the task as complited
    public void doTurn(Game game) {
        AnalyzeOutput analyzeOutput = MainAnalyze.getFacts(game);
        game.debug("attacks:\n" + analyzeOutput.toString());
        //get all facts and now do next stages - still not in OOP
        Calculator calculatorOutput = new Calculator();
        calculatorOutput.calc(game, analyzeOutput);
        game.debug("attacks:\n" + calculatorOutput.toString() +"\n");
        game.debug(calculatorOutput.getDecisions().size() + "\n");
        ExecuteMain executeMain = new ExecuteMain();
        game.debug(executeMain.toString());
        executeMain.execute(calculatorOutput.getDecisions());
        //end turn
    }
}
