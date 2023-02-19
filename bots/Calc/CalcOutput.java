package bots.Calc;

import java.util.LinkedList;
import java.util.List;

import bots.Calc.CalcObject.ExecuteMessage;
import bots.Fact.AnalyzeOutput;
import bots.Fact.FactObject.CanAttackFact;
import bots.Fact.FactObject.UnderAttackFact;
import penguin_game.Game;

public class CalcOutput {
    public List<CanAttackFact> icebergsToAttack = new LinkedList<CanAttackFact>();
    public List<ExecuteMessage> announcementsToPerform = new LinkedList<ExecuteMessage>();
    public List<UnderAttackFact> icebergsToProtect = new LinkedList<UnderAttackFact>();

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
