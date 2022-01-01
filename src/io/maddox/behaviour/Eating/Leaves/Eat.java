package io.maddox.behaviour.Eating.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;


public class Eat extends Leaf {

    @Override
    public boolean isValid() {
        return Players.local().healthPercent() < Configs.toEat && !Inventory.stream().id(Configs.WINE_ID).isEmpty();
    }

    @Override
    public int onLoop() {
        Item wine = Inventory.stream().id(Configs.WINE_ID).first();
        if (wine.valid()) {
            if (wine.interact("Drink")) {
                Condition.wait(() -> Players.local().healthPercent() > Configs.toEat, 250, 150);
            }
        }
        return 0;
    }
}
