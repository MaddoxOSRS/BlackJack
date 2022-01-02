package io.maddox.behaviour.EscapeCombat.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;

public class ClimbUp extends Leaf {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return Npcs.stream().interactingWithMe().first().valid() && bandit.animation() == 395 || Npcs.stream().interactingWithMe().first().valid() && bandit.animation() == 390;
    }


    @Override
    public int onLoop() {
        GameObject ladderdownstairs = Objects.stream().at(dyeLadderdownstairs).name("Ladder").nearest().first();
        if (ladderdownstairs.valid()) {
            if (Configs.house.contains(Players.local())) {
                System.out.println("firing inside");
                ladderdownstairs.interact("Climb-up");
                Condition.wait(() -> Configs.upstairs.contains(Players.local()), 250, 75);
            } else {
                GameObject closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
                if (closedcurtain.inViewport() || closedcurtain.valid()) {
                    System.out.println("Closing curtain...");
                    closedcurtain.interact("Open");
                    Condition.wait(() -> !closedcurtain.valid() && Players.local().animation() == -1
                            && !Players.local().inMotion(), 250, 50);
                }
                GameObject stairsdownstairs = Objects.stream().at(menaStairs).name("Staircase").nearest().first();
                if (stairsdownstairs != GameObject.getNil()) {
                    if (Configs.house.contains(Players.local())) {
                        System.out.println("firing inside");
                        stairsdownstairs.interact("Climb-up");
                        Condition.wait(() -> Configs.upstairs.contains(Players.local()), 250, 10);
                    }
                }
            }
        }
        return 0;
    }
}