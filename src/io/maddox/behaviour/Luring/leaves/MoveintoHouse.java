package io.maddox.behaviour.Luring.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;


public class MoveintoHouse extends Leaf {

    @Override
    public boolean isValid() {
        return  Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first().valid() && !Configs.house.contains(Players.local());
    }

    @Override
    public int onLoop() {
        Movement.moveTo(Configs.lure);
        return 0;
    }
}