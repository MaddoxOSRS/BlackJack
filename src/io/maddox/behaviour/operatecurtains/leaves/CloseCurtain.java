package io.maddox.behaviour.operatecurtains.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class CloseCurtain extends Leaf {

    GameObject openedcurtain;

    @Override
    public boolean isValid() {
        return Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first().valid()
                && Configs.house.contains(Players.local()) &&
                Objects.stream().at(Configs.curtain).id(Configs.openCurtain).nearest().first().valid();
    }

    @Override
    public int onLoop() {
        openedcurtain = Objects.stream().at(Configs.curtain).id(Configs.openCurtain).nearest().first();
        Movement.running(false);
        if (Npcs.stream().interactingWithMe().isEmpty() && openedcurtain.valid()
                || !Npcs.stream().interactingWithMe().isEmpty() && openedcurtain.valid()) {
            System.out.println("Closing curtain...");
            if (!openedcurtain.interact("Close")) {
                Condition.wait(() -> !openedcurtain.valid()
                        && !Players.local().inMotion(), 250, 50);
            }

        }
        return 0;
    }
}