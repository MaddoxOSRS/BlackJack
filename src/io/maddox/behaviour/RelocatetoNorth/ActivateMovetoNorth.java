package io.maddox.behaviour.RelocatetoNorth;

import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.NorthZone;

public class ActivateMovetoNorth extends Branch {

    @Override
    public boolean isValid() {
        return !Inventory.stream().id(1993).isEmpty() && !NorthZone.contains(Players.local());
    }
}
