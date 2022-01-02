package io.maddox.behaviour.operatecurtains.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class OperateCurtain extends Leaf {

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
            System.out.println("Opening curtain...");
            closedcurtain.interact("Open");
            Condition.wait(() -> !closedcurtain.valid() && Players.local().animation() == -1
                    && !Players.local().inMotion(), 250, 50);
        }
            if (!closedcurtain.valid()) {
                Movement.walkTo(Configs.downstairs);
                Condition.wait(() -> Players.local().animation() == -1 && Configs.house.contains(Players.local())
                        && !Players.local().inMotion(), 175, 50);
            }
        return 0;
    }
}