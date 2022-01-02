package io.maddox.behaviour.Eating.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;



public class OpenMoneyPouch extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.moneyPouch).count() == 28;
    }

    @Override
    public int onLoop() {
                Item pouch = Inventory.stream().name(Configs.moneyPouch).first();
                pouch.interact("Open-all");
        return 0;
    }
}

