package io.maddox.behaviour.EscapeCombat.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.DYEHOUSE;
import static io.maddox.data.Areas.DYEHOUSEupstairs;

public class Escape extends Leaf {
    Npc bandit;
    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(DYEHOUSE).id(735).nearest().first();
        return Npcs.stream().interactingWithMe().first().valid() && bandit.animation() == 390
                || DYEHOUSEupstairs.contains(Players.local());
    }

    @Override
    public int onLoop() {
        GameObject ladderdownstairs = Objects.stream().within(5).name("Ladder").nearest().first();
        GameObject ladderupstairs = Objects.stream().within(DYEHOUSEupstairs).name("Ladder").nearest().first();
        System.out.println("here first");
        if (ladderdownstairs != GameObject.getNil()) {
            if (DYEHOUSE.contains(Players.local())) {
                System.out.println("firing inside");
                ladderdownstairs.interact("Climb-up");
                Condition.wait(() -> DYEHOUSEupstairs.contains(Players.local()), 250, 10);
            }
        }
        System.out.println("second first");
        if (DYEHOUSEupstairs.contains(Players.local())) {
            ladderupstairs.interact("Climb-down");
            Condition.wait(() -> DYEHOUSE.contains(Players.local()), 250, 10);

        }
        return 0;
    }
}