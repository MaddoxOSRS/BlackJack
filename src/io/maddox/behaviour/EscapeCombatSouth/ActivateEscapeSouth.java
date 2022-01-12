package io.maddox.behaviour.EscapeCombatSouth;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.SouthZone;
import static io.maddox.data.Areas.SouthZoneupstairs;


public class ActivateEscapeSouth extends Branch {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first();
        return Configs.cantKnock && Configs.zone == SouthZone
                || Configs.cantKnock && Configs.zone == SouthZone ||
                !Configs.timetoJet() && Configs.upstairs.contains(Players.local()) && Configs.zoneupstairs == SouthZoneupstairs;
    }
}
