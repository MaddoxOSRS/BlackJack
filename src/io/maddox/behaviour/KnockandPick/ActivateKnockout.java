package io.maddox.behaviour.KnockandPick;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.*;


public class ActivateKnockout extends Branch {

    GameObject closedcurtain;
    @Override
    public boolean isValid() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && Inventory.stream().name(Configs.food).action("Eat").isNotEmpty()
                && Configs.house.contains(Players.local()) && !Configs.cantKnock &&
                closedcurtain.valid() || Players.local().healthPercent() < Configs.toEat || Players.local().healthPercent() < Configs.toEat
                && !Inventory.stream().name(Configs.food).action("Eat").isEmpty()||
                Players.local().healthPercent() < Configs.toEat && !Inventory.stream().name(Configs.food).action("Drink").isEmpty() || Inventory.stream().name(Configs.moneyPouch).count() <= 15;
    }

}
