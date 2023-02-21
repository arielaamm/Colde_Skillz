package bots.Calc;

import java.util.HashMap;
import java.util.Map;

import bots.MapPrediction;
import bots.Fact.AnalyzeOutput;
import bots.Fact.FactObject.*;
import penguin_game.Game;
import penguin_game.Iceberg;


public class Calculator {
    public static CalcOutput calc(Game game, AnalyzeOutput analyzeOutput) {
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

    static CalcOutput firstPart(Game game, AnalyzeOutput analyzeOutput) {
        CalcOutput calcOutput = new CalcOutput();
        calcOutput.announcementsToPerform.addAll(CalcuatorHelper.Upgrade(game, analyzeOutput));
        UnderAttackFact attackFact;
        for (Fact underAttackFact : analyzeOutput.alerts) {
            Map<Iceberg,Integer> mapfreePeng = new HashMap<>();
            attackFact = ((UnderAttackFact)underAttackFact);
            for (Iceberg myIceberg : game.getMyIcebergs()) {
                mapfreePeng.put(myIceberg, MapPrediction.getAmountFreePenguin(myIceberg, game));
            }
            mapfreePeng = CalcuatorHelper.defend(/*....*/);
//....
        }
        for (Fact canAttackFact : analyzeOutput.attacks) {
            
        }
    }

    

    static CalcOutput SecondPart(Game game, AnalyzeOutput analyzeOutput) {
        CalcOutput calcOutput = new CalcOutput();
        calcOutput.announcementsToPerform.addAll(CalcuatorHelper.Upgrade(game, analyzeOutput));
    }

    static CalcOutput ThiredPart(Game game, AnalyzeOutput analyzeOutput) {
        CalcOutput calcOutput = new CalcOutput();
        calcOutput.announcementsToPerform.addAll(CalcuatorHelper.Upgrade(game, analyzeOutput));
    }
}
