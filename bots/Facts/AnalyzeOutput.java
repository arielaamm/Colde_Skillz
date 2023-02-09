package bots.Facts;

import java.util.List;

public class AnalyzeOutput {
    public List<Alert> alerts;
    public List<Attack> attacks;
    public List<Announcement> announcements;

    public AnalyzeOutput(List<Alert> alerts, List<Attack> attacks, List<Announcement> announcements) {
        this.alerts = alerts;
        this.attacks = attacks;
        this.announcements = announcements;
    }

    @Override
    public String toString() {
        return "AnalyzeOutput [alerts=" + alerts.toString() + ", attacks=" + attacks.toString() + ", announcements=" + announcements.toString() + "]\n";
    }

}
