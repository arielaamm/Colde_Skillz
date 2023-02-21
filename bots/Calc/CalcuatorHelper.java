package bots.Calc;

import java.util.*;

import bots.MapPrediction;
import bots.Calc.CalcObject.ExecuteMessage;
import bots.Fact.AnalyzeOutput;
import bots.Fact.FactObject.*;
import penguin_game.*;

public class CalcuatorHelper {
    public static List<ExecuteMessage> Upgrade(Game game, AnalyzeOutput analyzeOutput) {
        boolean flag = false;
        List<ExecuteMessage> list = new LinkedList<>();
        for (Fact factAnnouncements : analyzeOutput.announcements) {
            Iceberg icebergToUpgrade = ((CanUpgradeMessage) factAnnouncements).getSource();
            if (factAnnouncements.getDescription() == "CanUpgrade") {
                for (Fact factAlerts : analyzeOutput.alerts) {
                    PenguinGroup penguinGroupOfAlert = ((UnderAttackFact) factAlerts).getPenguinGroup();
                    if (((Iceberg) penguinGroupOfAlert.destination) == icebergToUpgrade) {
                        flag = true;
                        int amountAtIceberg = MapPrediction.AmountAtIceberg(penguinGroupOfAlert.turnsTillArrival,
                                icebergToUpgrade, game);
                        if (penguinGroupOfAlert.penguinAmount + 2 < amountAtIceberg) {
                            list.add(new ExecuteMessage("Upgrade", icebergToUpgrade));
                        }
                    }
                }
                if (!flag) {
                    list.add(new ExecuteMessage("Upgrade", icebergToUpgrade));
                }
            }
        }
        return list;

    }
}
