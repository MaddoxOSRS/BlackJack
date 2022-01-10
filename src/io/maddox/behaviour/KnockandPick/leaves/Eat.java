package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.stream.item.InventoryItemStream;

import static io.maddox.data.Configs.knockCount;
import static io.maddox.data.Configs.toEat;


public class Eat extends Leaf {
    @Override
    public boolean isValid() {
        return Players.local().healthPercent() < Configs.toEat
                && !Inventory.stream().name(Configs.food).action("Eat").isEmpty()
                && !Players.local().healthBarVisible() ||
                Players.local().healthPercent() < Configs.toEat && !Inventory.stream().name(Configs.food).action("Drink").isEmpty() && !Players.local().healthBarVisible();
    }

    @Override
    public int onLoop() {
        Item food = Inventory.stream().filtered(item -> item.valid() && !item.stackable() && (item.actions().contains("Eat") || item.actions().contains("Drink"))).first();
        if (food.valid()) {
            if (food.actions().size() > 0) {
                if (food.interact(food.actions().get(0))) {
                    Condition.wait(() -> Players.local().healthPercent() > toEat || Players.local().animation() == -1
                            && !Players.local().inMotion(), 150, 50);
                    knockCount = 0;
                }
             }
        }
        return 0;
    }
}
