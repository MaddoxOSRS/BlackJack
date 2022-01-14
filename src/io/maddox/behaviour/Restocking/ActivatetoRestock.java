package io.maddox.behaviour.Restocking;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;

public class ActivatetoRestock extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.stream().filtered(i -> !i.stackable() && (i.actions().contains("Eat") || i.actions().contains("Drink"))).isEmpty();
    }
}
