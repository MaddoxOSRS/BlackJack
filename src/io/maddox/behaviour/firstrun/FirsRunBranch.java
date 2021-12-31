package io.maddox.behaviour.firstrun;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;

public class FirsRunBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Configs.started;
    }
}
