package io.maddox.behaviour.Luring;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.*;

public class ActivateLure extends Branch {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.missingThug).id(Configs.thug).nearest().first();
        return !Configs.missingThug.contains(bandit) && Configs.zone.contains(Players.local()) && !Inventory.stream().id(1993).isEmpty();
    }
}
