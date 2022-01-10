package io.maddox.behaviour.HopWorlds.Leaves;

import io.maddox.data.Configs;
import io.maddox.data.WorldHopping;
import io.maddox.framework.Leaf;


public class HopWorld extends Leaf {


    @Override
    public boolean isValid() {
        return Configs.nearPlayer();
    }

    @Override
    public int onLoop() {
            Configs.cantKnock = false;
            WorldHopping.Hop();
        return 0;
    }
}
