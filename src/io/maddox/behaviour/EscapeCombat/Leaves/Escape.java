package io.maddox.behaviour.EscapeCombat.Leaves;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;

public class Escape extends Leaf {
    Npc bandit;
    @Override
    public boolean isValid() {
         bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return Players.local().spotAnimation() > 56 || DYEHOUSEupstairs.contains(Players.local());
    }

    @Override
    public int onLoop() {
        GameObject ladderdownstairs = Objects.stream().at(dyeLadder).name("Ladder").nearest().first();
        GameObject ladderupstairs = Objects.stream().within(DYEHOUSEupstairs).name("Ladder").nearest().first();
        GameObject stairsdownstairs = Objects.stream().at(menaStairs).name("Ladder").nearest().first();
        GameObject stairsupstairs = Objects.stream().at(menaStairs).name("Ladder").nearest().first();
        System.out.println("here first");
        if (Configs.zone == NorthZone) {
                if (Configs.house.contains(Players.local())) {
                    System.out.println("firing inside");
                    ladderdownstairs.interact("Climb-up");
                    Condition.wait(() -> DYEHOUSEupstairs.contains(Players.local()), 250, 10);
            }
            System.out.println("second first");
            if (DYEHOUSEupstairs.contains(Players.local())) {
                ladderupstairs.interact("Climb-down");
                Condition.wait(() -> Configs.house.contains(Players.local()), 250, 10);

            }
        } else {
            GameObject closedcurtain = Objects.stream().at(Configs.curtain).id(1533).nearest().first();
            if (closedcurtain.inViewport() || closedcurtain.valid()) {
                closedcurtain.interact("Open");
                Condition.sleep(Random.nextInt(1152, 1757));
            }
            if (stairsdownstairs != GameObject.getNil()) {
                if (Configs.house.contains(Players.local())) {
                    System.out.println("firing inside");
                    stairsdownstairs.interact("Climb-up");
                    Condition.wait(() -> menaUpstairs.contains(Players.local()), 250, 10);
                }
                System.out.println("second first");
                if (menaUpstairs.contains(Players.local())) {
                    ladderupstairs.interact("Climb-down");
                    Condition.wait(() -> !menaUpstairs.contains(Players.local()), 250, 10);
                }
            }
    }
        return 0;
    }
}