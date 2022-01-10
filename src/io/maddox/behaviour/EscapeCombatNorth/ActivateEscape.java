package io.maddox.behaviour.EscapeCombatNorth;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.NorthZone;
import static io.maddox.data.Areas.NorthZoneupstairs;


public class ActivateEscape extends Branch {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return Configs.cantKnock && Configs.zone == NorthZone && Players.local().healthPercent() < Configs.toEat ||
                !Configs.timetoJet() && Configs.upstairs.contains(Players.local()) && Configs.zoneupstairs == NorthZoneupstairs;
    }
}
