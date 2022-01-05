package io.maddox.behaviour.Eating.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

import static io.maddox.data.Configs.knockCount;
import static io.maddox.data.Configs.pickCount;


public class OpenMoneyPouch extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.moneyPouch).count() == 28;
    }

    @Override
    public int onLoop() {
                Item pouch = Inventory.stream().name(Configs.moneyPouch).first();
        if(pouch.interact("Open-all")) {
            knockCount = 0;
            pickCount = 0;
            Condition.wait(() -> Inventory.stream().name(Configs.moneyPouch).count() < 1, 1000, 5);
        }
        return 0;
    }
}

