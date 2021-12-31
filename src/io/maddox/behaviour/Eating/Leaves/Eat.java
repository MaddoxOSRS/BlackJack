package io.maddox.behaviour.Eating.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class Eat extends Leaf {

    @Override
    public boolean isValid() {
        return Players.local().healthPercent() <= Configs.toEat && !Inventory.stream().id(1993).isEmpty();
    }

    @Override
    public int onLoop() {
        Item wine = Inventory.stream().id(1993).first();
        if (wine.valid()) {
            if (wine.interact("Drink")) {
                Condition.sleep(Random.nextInt(200, 500));
            }
        }
        return 0;
    }
}
