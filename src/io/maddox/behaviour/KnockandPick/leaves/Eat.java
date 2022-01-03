package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Configs.*;


public class Eat extends Leaf {

    @Override
    public boolean isValid() {
        return Players.local().healthPercent() < Configs.toEat && !Inventory.stream().id(Configs.WINE_ID).isEmpty();
    }

    @Override
    public int onLoop() {
        Item wine = Inventory.stream().id(Configs.WINE_ID).first();
        if (wine.valid()) {
            wine.interact("Drink");
            }
        return 0;
    }
}
