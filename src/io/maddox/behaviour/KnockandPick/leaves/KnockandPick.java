package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.*;
import static java.lang.System.out;


public class KnockandPick extends Leaf {

    Npc bandit;
    GameObject openedcurtain;

    @Override
    public boolean isValid() {
        return house.contains(Npcs.stream().within(house).id(thug).nearest().first())
                && !Inventory.stream().id(WINE_ID).isEmpty() && house.contains(Players.local())
                && Players.local().healthPercent() > toEat && knockCount == 0;
    }

    @Override
    public int onLoop() {
        openedcurtain = Objects.stream().at(curtain).id(openCurtain).nearest().first();
        Movement.running(false);
        if (Npcs.stream().interactingWithMe().first().valid() && openedcurtain.valid()
                || !Npcs.stream().interactingWithMe().first().valid() && openedcurtain.valid()) {
            out.println("Closing curtain...");
            openedcurtain.interact("Close");
            Condition.wait(() -> Players.local().animation() == -1
                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
        }
        bandit = Npcs.stream().within(house).id(thug).nearest().first();
        if (!bandit.inViewport()) {
            Camera.turnTo(bandit);
        }
        if (bandit.valid()) {
            if (knockCount < 1 && bandit.animation() <= 808 && bandit.interact("Knock-Out")) {
                out.println("Knocking out...");
                pickCount = 0;
                Condition.wait(() ->
                        Players.local().animation() == 401
                        || Chat.canContinue(), 250, 1);
                knockCount++;

            }
        }
        return 0;
    }
}