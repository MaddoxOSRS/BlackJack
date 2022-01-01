package io.maddox.behaviour.RelocatetoNorth.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;


public class MovetoBandit extends Leaf {


    @Override
    public boolean isValid() {
        return !Inventory.stream().id(Configs.WINE_ID).isEmpty() && !Configs.zone.contains(Players.local());
    }

    @Override
    public int onLoop() {
        Movement.moveTo(Configs.movement);
        return 0;
    }
}