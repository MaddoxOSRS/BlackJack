package io.maddox.behaviour.EscapeCombatNorth.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.*;


public class ClimbDown extends Leaf {

    @Override
    public boolean isValid() {
        return !Configs.inCombat() && Configs.upstairs.contains(Players.local());
    }

    @Override
    public int onLoop() {
        GameObject ladderupstairs = Objects.stream().at(Configs.escapedown).id(Configs.climbdownladder).nearest().first();
        if (ladderupstairs.valid()) {
            ladderupstairs.interact("Climb-down");
            System.out.println("Good luck");
            Condition.wait(() -> Configs.house.contains(Players.local()), 1000, 25);
            cantKnock = false;
        }
        return 0;
    }
}