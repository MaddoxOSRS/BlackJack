package io.maddox.behaviour.Luring;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;


public class ActivateLure extends Branch {
    Npc bandit;
    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return !Configs.house.contains(bandit)
                && Configs.zone.contains(Players.local())
                && Inventory.stream().name(Configs.food).isNotEmpty() || Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first().valid() && !Configs.house.contains(Players.local());
    }
}
