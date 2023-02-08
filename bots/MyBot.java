package bots;

import bots.Analyze.MainAnalyze;
import bots.Culculator.Calculator;
import bots.Executer.ExecuteMain;
import bots.Facts.AnalyzeOutput;
import penguin_game.*;

public class MyBot implements SkillzBot {

    //[ ]: complite this func, and if so mark the task as complited
    public void doTurn(Game game) {
        /*?: why do u create this MainAnalyze if u only need him for his getFacts()?
        ANSWER: In OOP use the little you can in ססטיאכס
        so i made MainAnalyze to abstract class and the getFacts to static and new u dont need to create instance 
        */
        // MainAnalyze mainAnalyze = new MainAnalyze(); 

        AnalyzeOutput output = MainAnalyze.getFacts(game);// return the analysis
        AnalyzeOutput analyzeOutput = MainAnalyze.getFacts(game);
        game.debug("number of attacks:");
        game.debug(analyzeOutput.attacks.size());
        game.debug(analyzeOutput.attacks.get(0).getDescription());
        //get all facts and now do next stages - still not in OOP
        Calculator calculator = new Calculator();
        calculator.calc(game, output);
        game.debug("number of decisions:");
        game.debug(calculator.getDecisions().size());
        ExecuteMain executeMain = new ExecuteMain();
        game.debug(calculator.getDecisions());
        executeMain.execute(calculator.getDecisions());
        //end turn
    }
}
