package io.maddox.behaviour.Restocking;

import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;



public class ActivatetoRestock extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.stream().id(1993).isEmpty();
    }
}
