package io.maddox.behaviour.KnockandPick;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.DYEHOUSE;
import static io.maddox.data.Areas.NorthZone;

public class ActivateKnockout extends Branch {
    Npc bandit;
    Npc attackingBandit;
    @Override
    public boolean isValid() {
        attackingBandit = Npcs.stream().id(735).filter(npc -> npc.interacting().valid()).nearest().first();
        bandit = Npcs.stream().within(DYEHOUSE).id(735).nearest().first();
        return DYEHOUSE.contains(Npcs.stream().within(2).id(735).nearest().first()) && !(bandit.animation() == 390) && DYEHOUSE.contains(Players.local()) && !Inventory.stream().id(1993).isEmpty();
    }
}
