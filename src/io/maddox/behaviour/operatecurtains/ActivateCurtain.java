package io.maddox.behaviour.operatecurtains;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class ActivateCurtain extends Branch {

    GameObject closedcurtain;
    @Override
    public boolean isValid() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
        return Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first().valid()
                && !Configs.house.contains(Players.local()) &&
                Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first().valid();
    }


}
