package io.maddox.behaviour.RelocatetoNorth;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;



public class ActivateMovetoBandit extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.food).isNotEmpty() && !Configs.house.contains(Players.local());
    }
}
