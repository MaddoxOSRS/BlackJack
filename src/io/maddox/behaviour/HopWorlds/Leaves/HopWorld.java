package io.maddox.behaviour.HopWorlds.Leaves;

import io.maddox.data.Configs;

import io.maddox.data.WorldHopping;
import io.maddox.framework.Leaf;

import static io.maddox.data.Configs.knockCount;
import static io.maddox.data.Configs.pickCount;


public class HopWorld extends Leaf {


    @Override
    public boolean isValid() {
        return Configs.nearPlayer();
    }

    @Override
    public int onLoop() {
        if (Configs.nearPlayer()) {
            knockCount = 0;
            pickCount = 0;
            WorldHopping.Hop();
        }
        return 0;
    }
}
