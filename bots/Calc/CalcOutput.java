package bots.Calc;

import java.util.LinkedList;
import java.util.List;

import bots.Calc.CalcObject.ExecuteCanAttack;
import bots.Calc.CalcObject.ExecuteMessage;
import bots.Calc.CalcObject.ExecuteUnderAttack;
import bots.Fact.AnalyzeOutput;
import penguin_game.Game;

public class CalcOutput {
    public List<ExecuteCanAttack> icebergsToAttack = new LinkedList<ExecuteCanAttack>();
    public List<ExecuteMessage> announcementsToPerform = new LinkedList<ExecuteMessage>();
    public List<ExecuteUnderAttack> icebergsToProtect = new LinkedList<ExecuteUnderAttack>();

    public CalcOutput(Game game, AnalyzeOutput analyzeOutput) {
        CalcOutput calcOutput = Calculator.calc(game, analyzeOutput);
        icebergsToAttack = calcOutput.icebergsToAttack;
        icebergsToProtect = calcOutput.icebergsToProtect;
        announcementsToPerform = calcOutput.announcementsToPerform;
    }

    public CalcOutput() {}

    @Override
    public String toString() {
        return "CalcOutput [icebergsToAttack=" + icebergsToAttack + ", announcementsToPerform=" + announcementsToPerform
                + ", icebergsToProtect=" + icebergsToProtect + "]";
    }
}
