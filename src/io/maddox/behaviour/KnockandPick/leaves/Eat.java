package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Configs.knockCount;
import static io.maddox.data.Configs.toEat;


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
            knockCount = 0;
            }
        return 0;
    }
}
