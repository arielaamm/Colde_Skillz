package bots;

import bots.Analyze.MainAnalyze;
import bots.Culculator.Calculator;
import bots.DataBases.Knowledge;
import bots.Executer.ExecuteMain;
import bots.Facts.AnalyzeOutput;
import penguin_game.*;

public class MyBot implements SkillzBot {
    //check
    public void doTurn(Game game) {
        Knowledge knowledge = Knowledge.getInstance();
        game.debug("start turn, int part number: " + knowledge.getPartInGameNumber());
        if (game.getEnemyIcepitalIcebergs().length == 0) {
            return;
        }
        AnalyzeOutput analyzeOutput = MainAnalyze.getFacts(game);
        game.debug("attacks:\n" + analyzeOutput.toString());
        //get all facts & now do next stages - still not in OOP
        Calculator calculatorOutput = new Calculator();
        calculatorOutput.calc(game, analyzeOutput);
        game.debug("attacks:\n" + calculatorOutput.toString());
        game.debug(calculatorOutput.getDecisions().size() + "\n");
        ExecuteMain executeMain = new ExecuteMain();
        game.debug(executeMain.toString());
        executeMain.execute(calculatorOutput.getDecisions());
        //end turn
    }
}
