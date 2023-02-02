package bots;

import java.util.LinkedList;
import java.util.List;

import penguin_game.Iceberg;

public abstract class betterIcebergTool {

    /**
     * cast from {@link Iceberg} to {@link betterIceberg}
     * 
     * @param icebergs[]
     * @return List<betterIceberg>
     */
    public static List<betterIceberg> cast(Iceberg[] icebergs) {
        List<betterIceberg> castIcebergs = new LinkedList<betterIceberg>();
        for (Iceberg iceberg : icebergs) {
            castIcebergs.add(new betterIceberg(iceberg));
        }
        return castIcebergs;
    }

    public static Iceberg getTheMostFertileIceberg(Iceberg[] icebergs)
    {
        if (icebergs.length == 0)
        {
            return null;
        }
        Iceberg mostFertileIceberg = icebergs[0]; 
        for (Iceberg iceberg : icebergs) {
            if (mostFertileIceberg.penguinAmount < iceberg.penguinAmount)
            {
                mostFertileIceberg = iceberg;
            }
        }
        return mostFertileIceberg;
    }
    
}
