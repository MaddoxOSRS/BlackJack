package io.maddox.behaviour.Luring.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class OpentoEnterHouse extends Leaf {

    GameObject closedcurtain;
    @Override
    public boolean isValid() {

        return Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first().valid()
                && !Configs.house.contains(Players.local()) &&
                Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first().valid();
    }

    @Override
    public int onLoop() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
        Movement.running(false);
        if (!Npcs.stream().interactingWithMe().first().valid() && closedcurtain.inViewport()) {
            System.out.println("Closing curtain...");
            closedcurtain.interact("Open");
            Condition.wait(() -> Players.local().animation() == -1
                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
        }
        return 0;
    }
}