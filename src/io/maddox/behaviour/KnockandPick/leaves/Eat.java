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
                && !Inventory.stream().name(Configs.food).action("Eat").isEmpty()||
                Players.local().healthPercent() < Configs.toEat && !Inventory.stream().name(Configs.food).action("Drink").isEmpty();
    }

    @Override
    public int onLoop() {
        Item food = Inventory.stream().filtered(i -> i.valid() && !i.stackable() && (i.actions().contains("Eat") || i.actions().contains("Drink"))).first();
        if (food.valid()) {
            if (food.actions().size() > 0) {
                if (food.interact(food.actions().get(0))) { //Interacts with first option, I.E. Eat, or Drink
                    Condition.wait(() -> Players.local().healthPercent() > toEat, 50, 50);
                    knockCount = 0;
                }
             }
        }
        return 0;
    }
}
