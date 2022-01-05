package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Configs.*;


public class Eat extends Leaf {
Item emptyJug;
    @Override
    public boolean isValid() {
        return Players.local().healthPercent() < Configs.toEat && !Inventory.stream().id(Configs.WINE_ID).isEmpty() && !Players.local().healthBarVisible();
    }

    @Override
    public int onLoop() {
        Item wine = Inventory.stream().id(Configs.WINE_ID).first();
        if (wine.valid()) {
            wine.interact("Drink");
            Condition.wait(() -> Players.local().healthPercent() > toEat || Players.local().animation() == -1
                    && !Players.local().inMotion(), 150, 50);
            }
        emptyJug = Inventory.stream().name(jug).first();
        if(emptyJug.interact("Drop")) {
            Condition.wait(() -> Inventory.stream().name(Configs.jug).isEmpty() || Players.local().animation() == -1
                    && !Players.local().inMotion(), 150, 50);
        }
        Item pouch = Inventory.stream().name(Configs.moneyPouch).first();
        if(pouch.interact("Open-all")) {
            Condition.wait(() -> Inventory.stream().name(Configs.moneyPouch).isEmpty() || Players.local().animation() == -1
                    && !Players.local().inMotion(), 150, 50);
        }
        return 0;
    }
}
