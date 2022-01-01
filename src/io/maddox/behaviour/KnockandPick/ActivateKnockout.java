package io.maddox.behaviour.KnockandPick;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.*;


public class ActivateKnockout extends Branch {
    Npc bandit;
    Npc attackingBandit;
    @Override
    public boolean isValid() {
         attackingBandit = Npcs.stream().id(Configs.thug).filter(npc -> npc.interacting().valid()).nearest().first();
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(1993).isEmpty();
    }
}
