package bots.Calc;

import java.util.LinkedList;
import java.util.List;

import bots.Fact.AnalyzeOutput;
import bots.Fact.FactObject.CanAttackFact;
import bots.Fact.FactObject.MessageFact;
import bots.Fact.FactObject.UnderAttackFact;
import penguin_game.Game;

public class CalcOutput {
    public List<CanAttackFact> icebergsToAttack = new LinkedList<CanAttackFact>();
    public List<MessageFact> announcementsToPerform = new LinkedList<MessageFact>();
    public List<UnderAttackFact> icebergsToProtect = new LinkedList<UnderAttackFact>();

    public CalcOutput(Game game, AnalyzeOutput analyzeOutput){
        
    }

    

    @Override
    public String toString() {
        return "CalcOutput [icebergsToAttack=" + icebergsToAttack + ", announcementsToPerform=" + announcementsToPerform
                + ", icebergsToProtect=" + icebergsToProtect + "]";
    }
}
