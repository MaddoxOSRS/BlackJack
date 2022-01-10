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
    @Override
    public boolean isValid() {
        return Players.local().healthPercent() < Configs.toEat && !Inventory.stream().id(Configs.food).isEmpty() && !Players.local().healthBarVisible();
    }

    @Override
    public int onLoop() {
        Item food = Inventory.stream().id(Configs.food).first();
        if (food.valid()) {
            food.interact("Drink");
        } else {
            food.interact("Eat");
            Condition.wait(() -> Players.local().healthPercent() > toEat || Players.local().animation() == -1
                    && !Players.local().inMotion(), 150, 50);
            knockCount = 0;
            }
        return 0;
    }
}
