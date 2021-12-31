package io.maddox.behaviour.EscapeCombat;

import io.maddox.framework.Branch;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.DYEHOUSE;
import static io.maddox.data.Areas.DYEHOUSEupstairs;

public class ActivateEscape extends Branch {
    Npc bandit;
    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(DYEHOUSE).id(735).nearest().first();
        return Npcs.stream().interactingWithMe().first().valid() && bandit.animation() == 390
                || DYEHOUSEupstairs.contains(Players.local());
    }
}
