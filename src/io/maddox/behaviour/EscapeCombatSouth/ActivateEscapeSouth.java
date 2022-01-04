package io.maddox.behaviour.EscapeCombatSouth;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.SouthZone;


public class ActivateEscapeSouth extends Branch {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return !Npcs.stream().interactingWithMe().isEmpty() && bandit.animation() == 395 && Configs.zone == SouthZone
                || !Npcs.stream().interactingWithMe().isEmpty() && bandit.animation() == 390 && Configs.zone == SouthZone ||
                Npcs.stream().interactingWithMe().isEmpty() && Configs.upstairs.contains(Players.local());
    }
}
