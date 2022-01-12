package io.maddox.behaviour.KnockandPick;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.*;


public class ActivateKnockout extends Branch {
GameObject closedcurtain;
    @Override
    public boolean isValid() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
        return closedcurtain.valid() && Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first())
                && Configs.house.contains(Players.local())
                && !Configs.cantKnock || !Configs.house.contains(Players.local());
    }

}
