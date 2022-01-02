package io.maddox.behaviour.EscapeCombat;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;


public class ActivateEscape extends Branch {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return Npcs.stream().interactingWithMe().first().valid() && bandit.animation() == 395
                || Npcs.stream().interactingWithMe().first().valid() && bandit.animation() == 390 ||
                !Npcs.stream().interactingWithMe().first().valid() && Configs.upstairs.contains(Players.local());
    }
}
