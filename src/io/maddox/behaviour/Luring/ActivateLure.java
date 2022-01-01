package io.maddox.behaviour.Luring;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.*;


public class ActivateLure extends Branch {
    Npc bandit;
    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return !Configs.house.contains(bandit)
                && Configs.zone.contains(Players.local())
                && Inventory.stream().id(Configs.WINE_ID).isNotEmpty();
    }
}
