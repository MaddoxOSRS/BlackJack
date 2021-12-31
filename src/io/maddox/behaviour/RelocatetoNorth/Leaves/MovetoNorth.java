package io.maddox.behaviour.RelocatetoNorth.Leaves;

import io.maddox.framework.Leaf;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;

public class MovetoNorth  extends Leaf {


    @Override
    public boolean isValid() {
        return !Inventory.stream().id(1993).isEmpty() && !NorthZone.contains(Players.local());
    }

    @Override
    public int onLoop() {
        Movement.moveTo(outsideHouse);
        return 0;
    }
}