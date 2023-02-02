package bots.Facts;

import java.util.List;

public class AnalyzeOutput {
    public List<Alert> alerts;
    public List<Attack> attacks;
    public List<Announcement> announcements;
    public AnalyzeOutput(List<Alert> alerts1, List<Attack> attacks1, List<Announcement> announcements1) {
        alerts = alerts1;
        attacks = attacks1;
        announcements = announcements1;
    }

}
