package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;



public class OpenPouch extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.moneyPouch).count() <= 15;
    }

    @Override
    public int onLoop() {
        Item pouch = Inventory.stream().name(Configs.moneyPouch).first();
        if(pouch.interact("Open-all")) {
            Condition.wait(() -> Inventory.stream().name(Configs.moneyPouch).isEmpty() || Players.local().animation() == -1
                    && !Players.local().inMotion(), 150, 50);
        }
        return 0;
    }
}
