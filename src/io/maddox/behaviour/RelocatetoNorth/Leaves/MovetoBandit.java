package io.maddox.behaviour.RelocatetoNorth.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;

public class MovetoBandit extends Leaf {


    @Override
    public boolean isValid() {
        return !Inventory.stream().id(1993).isEmpty() && !Configs.zone.contains(Players.local());
    }

    @Override
    public int onLoop() {
        Movement.moveTo(Configs.movement);
        return 0;
    }
}