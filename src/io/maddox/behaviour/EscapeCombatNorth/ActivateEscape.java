package io.maddox.behaviour.EscapeCombatNorth;

import io.maddox.Main;
import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.NorthZone;


public class ActivateEscape extends Branch {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return !Npcs.stream().interactingWithMe().isEmpty() && bandit.animation() == 395 && Configs.cantKnock && Configs.zone == NorthZone
                || !Npcs.stream().interactingWithMe().isEmpty() && bandit.animation() == 390 && Configs.cantKnock && Configs.zone == NorthZone ||
                !Configs.inCombat() && Configs.upstairs.contains(Players.local()) && Configs.zone == NorthZone;
    }
}
