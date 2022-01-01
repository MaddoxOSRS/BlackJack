package io.maddox.behaviour.Eating;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;


public class ActivateEat extends Branch {

    @Override
    public boolean isValid() {
        return Players.local().healthPercent() < Configs.toEat && !Inventory.stream().id(Configs.WINE_ID).isEmpty();
    }
}
