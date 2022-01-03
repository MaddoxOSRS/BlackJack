package io.maddox.behaviour.Eating.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;



public class OpenMoneyPouch extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.moneyPouch).isNotEmpty();
    }

    @Override
    public int onLoop() {
                Item pouch = Inventory.stream().name(Configs.moneyPouch).first();
                if(!pouch.interact("Open-all")) {
                    Condition.wait(() -> Inventory.stream().name(Configs.moneyPouch).isEmpty(), 250, 150);
                }
        return 0;
    }
}

