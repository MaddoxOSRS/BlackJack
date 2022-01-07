package io.maddox.behaviour.KnockandPick;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.knockCount;


public class ActivateKnockout extends Branch {

    GameObject closedcurtain;
    @Override
    public boolean isValid() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(Configs.WINE_ID).isEmpty()
                && Configs.house.contains(Players.local()) && !Configs.cantKnock &&
                closedcurtain.valid() && Players.local().healthPercent() > Configs.toEat || Players.local().healthPercent() < Configs.toEat;
    }

}
