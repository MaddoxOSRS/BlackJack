package io.maddox.behaviour.HopWorlds;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;



public class ActivateWorldHop extends Branch {


    @Override
    public boolean isValid() {
        return Configs.nearPlayer();
    }
}
