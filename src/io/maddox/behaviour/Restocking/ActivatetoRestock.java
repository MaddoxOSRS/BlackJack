package io.maddox.behaviour.Restocking;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Configs.jug;


public class ActivatetoRestock extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.stream().id(Configs.food).isEmpty() && !Configs.inCombat() || Inventory.stream().name(jug).isNotEmpty() && Areas.inMarket.contains(Players.local());
    }
}
