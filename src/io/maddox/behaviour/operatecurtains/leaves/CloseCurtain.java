package io.maddox.behaviour.operatecurtains.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class CloseCurtain extends Leaf {

    GameObject openedcurtain;

    @Override
    public boolean isValid() {
        return Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first().valid()
                && Configs.house.contains(Players.local()) &&
                Objects.stream().at(Configs.curtain).id(Configs.openCurtain).nearest().first().valid();
    }

    @Override
    public int onLoop() {
        openedcurtain = Objects.stream().at(Configs.curtain).id(Configs.openCurtain).nearest().first();
        Movement.running(false);
            System.out.println("Closing curtain...");
            if (openedcurtain.valid()) {
                openedcurtain.interact("Close");
                Condition.wait(() -> !openedcurtain.valid()
                        && !Players.local().inMotion(), 250, 50);

        }
        return 0;
    }
}