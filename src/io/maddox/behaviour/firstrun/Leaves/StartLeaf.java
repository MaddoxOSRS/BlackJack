package io.maddox.behaviour.firstrun.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;


public class StartLeaf extends Leaf {
    @Override
    public boolean isValid() {
        System.out.println("1:startleaf " + !Configs.started);
        return !Configs.started;
    }

    @Override
    public int onLoop() {
        System.out.println("started");
            Configs.started = true;
        return 0;
    }
}
