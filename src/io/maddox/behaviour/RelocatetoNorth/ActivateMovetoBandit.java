package io.maddox.behaviour.RelocatetoNorth;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;



public class ActivateMovetoBandit extends Branch {

    @Override
    public boolean isValid() {
        return !Inventory.stream().id(Configs.WINE_ID).isEmpty() && !Configs.zone.contains(Players.local());
    }
}
