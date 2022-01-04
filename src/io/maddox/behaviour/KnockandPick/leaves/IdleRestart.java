package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;

import static io.maddox.data.Configs.*;


public class IdleRestart extends Leaf {


    @Override
    public boolean isValid() {
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(Configs.WINE_ID).isEmpty() && Configs.house.contains(Players.local())
                && Players.local().healthPercent() > Configs.toEat && Configs.isIdle();
    }

    @Override
    public int onLoop() {
                    pickCount = 0;
                    knockCount = 0;
        return 0;
    }
}