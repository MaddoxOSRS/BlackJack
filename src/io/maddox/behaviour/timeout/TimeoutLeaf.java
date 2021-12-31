package io.maddox.behaviour.timeout;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;

public class TimeoutLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public int onLoop() {
        return Configs.loopReturn;
    }
}
