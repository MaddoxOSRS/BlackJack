package io.maddox.behaviour.Restocking;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npcs;


public class ActivatetoRestock extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.stream().id(Configs.WINE_ID).isEmpty() && !Configs.inCombat();
    }
}
