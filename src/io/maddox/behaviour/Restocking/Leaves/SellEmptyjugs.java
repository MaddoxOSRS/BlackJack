package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.inMarket;
import static io.maddox.data.Configs.*;

public class SellEmptyjugs extends Leaf {

    Npc generalstore;

    @Override
    public boolean isValid() {
        return Inventory.stream().name(jug).isNotEmpty() && inMarket.contains(Players.local());
    }

    @Override
    public int onLoop() {
        generalstore = Npcs.stream().within(inMarket).id(Configs.generalStore).nearest().first();
        if (generalstore.valid()) {
            generalstore.interact("Trade");
            Condition.wait(() -> Store.opened(), 250, 150);
            if (Store.opened()) {
                Inventory.stream().name(jug).first().interact("Sell 50");
                Condition.wait(() -> Inventory.stream().name(jug).isEmpty(), 100, 50);
                if (Inventory.stream().name(jug).isEmpty()) {
                    Store.close();
                     }
            }
        }
        return 0;
    }
}
