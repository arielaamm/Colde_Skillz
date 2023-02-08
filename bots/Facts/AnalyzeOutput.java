package bots.Facts;

import java.util.List;

public class AnalyzeOutput {
    public List<Alert> alerts;
    public List<Attack> attacks;
    public List<Announcement> announcements; //?: when we coll Announcement?
    //ANSWER: not yet done. But all run un AnalyzeMain
    public AnalyzeOutput(List<Alert> alerts, List<Attack> attacks, List<Announcement> announcements) {
        this.alerts = alerts;
        this.attacks = attacks;
        this.announcements = announcements;
    }

}
