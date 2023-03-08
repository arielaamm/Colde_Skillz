package bots;

import java.util.ArrayList;
import java.util.List;

import bots.Analyze.MainAnalyze;
import bots.Culculator.Calculator;
import bots.DataBases.Knowledge;
import bots.Executer.ExecuteMain;
import bots.Facts.AnalyzeOutput;
import bots.Functions.DistanceFunctions;
import penguin_game.*;

public class MyBot implements SkillzBot {

    public void doTurn(Game game) throws CloneNotSupportedException {
        for (Iceberg iceberg : game.getMyIcebergs()) {
            game.debug(iceberg);
        }
        Knowledge knowledge = Knowledge.getInstance();
        knowledge.setGame(game);
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
