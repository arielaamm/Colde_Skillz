package bots.Fact;

import java.util.List;

import bots.Fact.FactObject.Fact;

public class AnalyzeOutput {
    public List<Fact> alerts;
    public List<Fact> attacks;
    public List<Fact> announcements;

    public AnalyzeOutput(List<Fact> alerts, List<Fact> attacks, List<Fact> announcements) {
        this.alerts = alerts;
        this.attacks = attacks;
        this.announcements = announcements;
    }

    @Override
    public String toString() {
        return "AnalyzeOutput [alerts=" + alerts.toString() + ", attacks=" + attacks.toString() + ", announcements=" + announcements.toString() + "]\n";
    }
}
