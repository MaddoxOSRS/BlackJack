package io.maddox.behaviour.EscapeCombatNorth.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class ClimbDown extends Leaf {

    @Override
    public boolean isValid() {
        return Npcs.stream().interactingWithMe().isEmpty() && Configs.upstairs.contains(Players.local());
    }

    @Override
    public int onLoop() {
        GameObject ladderupstairs = Objects.stream().at(Configs.escapedown).id(Configs.climbdownladder).nearest().first();
        if (ladderupstairs.valid()) {
            ladderupstairs.interact("Climb-down");
            System.out.println("Good luck");
            Condition.wait(() -> Configs.house.contains(Players.local()), 250, 50);
        }
        return 0;
    }
}