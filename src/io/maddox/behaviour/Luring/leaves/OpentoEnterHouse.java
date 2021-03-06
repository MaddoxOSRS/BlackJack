package io.maddox.behaviour.Luring.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class OpentoEnterHouse extends Leaf {
    Npc bandit;
    GameObject closedcurtain;
    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first();
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();

        return !Configs.house.contains(bandit) &&
                Configs.zone.contains(Players.local()) &&
                closedcurtain.valid() ||
                !Configs.house.contains(bandit) &&
                        Configs.house.contains(Players.local())
                        && closedcurtain.valid();
    }

    @Override
    public int onLoop() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
        Movement.running(false);
        if (Npcs.stream().interactingWithMe().isEmpty() && closedcurtain.inViewport()) {
            System.out.println("Closing curtain...");
            closedcurtain.interact("Open");
            Condition.wait(() -> !closedcurtain.valid() && Players.local().animation() == -1
                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
        }
        return 0;
    }
}