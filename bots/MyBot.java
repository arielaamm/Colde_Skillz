package bots;

import bots.Analyze.MainAnalyze;
import bots.Culculator.Calculator;
import bots.Exeuter.ExecuteMain;
import bots.Facts.AnalyzeOutput;
import penguin_game.*;

public class MyBot implements SkillzBot {

    //[ ]: complite this func, and if so mark the task as complited
    public void doTurn(Game game) {
        /*?: why do u create this MainAnalyze if u only need him for his getFacts()?
        so i made MainAnalyze to abstract class and the getFacts to static and new u dont need to create instance 
        */
        // MainAnalyze mainAnalyze = new MainAnalyze(); 

        AnalyzeOutput output = MainAnalyze.getFacts(game);// return the analysis

        //get all facts and now do next stages - still not in OOP
        Calculator calculator = new Calculator();
        calculator.calc(game, output);
        ExecuteMain executeMain = new ExecuteMain();
        executeMain.execute(calculator.getDecisions());
        //end turn













//        List<Alert> alerts = output.alerts;
//        Map<Iceberg, Integer> freePenguins = new HashMap<>(); //here i will save how many free penguins i have in each iceberg
//        for (Iceberg iceberg : game.getMyIcebergs()) {
//            freePenguins.put(iceberg, iceberg.penguinAmount);//defult values
//        }
//        for (Alert alert : alerts) {
//            if (alert.getDescription() == "UnderAttack") {
//                UnderAttackAlert underAttackAlert = (UnderAttackAlert) alert;
//                //have to save good amount of pengs to save the home
//                int sumOfAttackers = 0; //want to know how many attackers.
//                for (PenguinGroup pg : underAttackAlert.getAttackers()) {
//                    sumOfAttackers += pg.penguinAmount;
//                }
//                //sub the peng i can create while they walking
//                //finding the farest attack and sub num of turn * pruduction per turn
//                int furestTurn = 0;
//                for (PenguinGroup pg : underAttackAlert.getAttackers()) {
//                    if (furestTurn < pg.turnsTillArrival) {
//                        furestTurn = pg.turnsTillArrival;
//                    }
//                }
//                sumOfAttackers -= furestTurn;
//                if (sumOfAttackers < 0) {
//                    sumOfAttackers = 0; //cant be negetive
//                }
//                //update the num of free peng per iceberg
//                freePenguins.put(underAttackAlert.getTarget(), freePenguins.get(underAttackAlert.getTarget()) - sumOfAttackers);
//            }
//        }
//        //Ignore if lose some Icebergs
//        //now all free pengs will attack the target
//        Iceberg target = ((CanAttack) output.attacks.get(0)).getTarget();
//        for(Iceberg source : game.getMyIcebergs()) {
//            if (freePenguins.get(source) > 0) {
//                source.sendPenguins(target, freePenguins.get(source));
//            }
//        }


















//        Player enemy = game.getEnemy();
//        Map<Iceberg, Integer> underAttack = new HashMap<>();
//        for (PenguinGroup pg : enemy.penguinGroups) {
//            int allreadyOnTheWay = 0;
//            if (underAttack.get(pg.destination) != null) {
//                allreadyOnTheWay = underAttack.get(pg.destination);
//            }
//            if (pg.turnsTillArrival > 2) {
//                allreadyOnTheWay -= 1;
//            }
//            underAttack.put(pg.destination, pg.penguinAmount + allreadyOnTheWay);
//        }
//        int number = 0;
//        for (Iceberg ice : game.getMyIcebergs()) {
//            if (underAttack.get(ice) != null) {
//                if (ice.penguinAmount - underAttack.get(ice) >= 1) {
//                    number += 2;
//                    ice.sendPenguins(game.getEnemy().icebergs[0], ice.penguinAmount - underAttack.get(ice));
//                }
//            } else {
//                number += 2;
//                if (number == 8) {
//                    number = 0;
//                }
//                if (game.getAllIcebergs()[number] == ice) {
//                    ice.sendPenguins(game.getAllIcebergs()[0], ice.penguinAmount);
//                }
//                ice.sendPenguins(game.getAllIcebergs()[number], ice.penguinAmount);
//            }
//        }
//
    }
}
