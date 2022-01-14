package io.maddox.behaviour.EscapeCombatNorth.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.NorthZone;
import static io.maddox.data.Areas.dyeLadderdownstairs;
import static io.maddox.data.Configs.cantKnock;

public class ClimbUp extends Leaf {
    Npc bandit;
    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first();
        return Configs.cantKnock && Configs.zone == NorthZone;
    }




    @Override
    public int onLoop() {
        GameObject ladderdownstairs = Objects.stream().at(dyeLadderdownstairs).name("Ladder").nearest().first();
            if (ladderdownstairs.valid()) {
                if (Configs.house.contains(Players.local())) {
                    System.out.println("firing inside");
                    ladderdownstairs.interact("Climb-up");
                    Condition.wait(() -> Configs.upstairs.contains(Players.local()), 1000, 5);
                    cantKnock = false;

            }
        }
        return 0;
    }
}