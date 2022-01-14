package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;

import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;
import static io.maddox.data.Configs.toEat;


public class Eat extends Leaf {
    @Override
    public boolean isValid() {
        System.out.println("EatCheck2: "+!Inventory.stream().action("Eat","Drink").isEmpty());
        return Players.local().healthPercent() < Configs.toEat && Inventory.stream().filtered(i -> !i.stackable() && (i.actions().contains("Eat") || i.actions().contains("Drink"))).isNotEmpty();
    }

    @Override
    public int onLoop() {
        Item food = Inventory.stream().filtered(i -> !i.stackable() && (i.actions().contains("Eat") || i.actions().contains("Drink"))).first();
        if (food.valid()) {
                if (food.interact(food.actions().get(0))) { //Interacts with first option, I.E. Eat, or Drink
                    Condition.wait(() -> Players.local().healthPercent() > toEat, 50, 50);
             }
        }
        return 0;
    }
}