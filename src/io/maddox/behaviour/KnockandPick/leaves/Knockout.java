package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;


public class Knockout extends Leaf {

    Npc bandit;
    Npc attackingBandit;

    @Override
    public boolean isValid() {
        attackingBandit = Npcs.stream().id(735).filter(npc -> npc.interacting().valid()).nearest().first();
        bandit = Npcs.stream().within(DYEHOUSE).id(735).nearest().first();
        return Players.local().healthPercent() > Configs.toEat && DYEHOUSE.contains(bandit) && NorthZone.contains(Players.local()) && !Inventory.stream().id(1993).isEmpty();
    }

    @Override
    public int onLoop() {
        GameObject openedcurtain = Objects.stream().within(DYEHOUSE).action("Close").name("Curtain").nearest().first();
        if (DYEHOUSE.contains(Players.local()) && DYEHOUSE.contains(bandit)) {
            if (openedcurtain.inViewport()) {
                System.out.println("Closing curtain...");
                openedcurtain.interact("Close");
                Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), 250, 5);
            }
            if (!bandit.inViewport()) {
                Camera.turnTo(bandit);
            }
            if (bandit.animation() <= 808) {
                if (bandit.interact("Knock-Out")) {
                    System.out.println("Knocking out...");
                    Condition.wait(() -> bandit.animation() >= 838, 300, 10);
                }
            }
        }
        return 0;
    }
}
