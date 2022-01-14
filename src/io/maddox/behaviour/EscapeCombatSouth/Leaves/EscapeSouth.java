package io.maddox.behaviour.EscapeCombatSouth.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.SouthZone;
import static io.maddox.data.Areas.menaStairs;
import static io.maddox.data.Configs.cantKnock;

public class EscapeSouth extends Leaf {
    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first();
        return Configs.cantKnock && Configs.zone == SouthZone;
    }


    @Override
    public int onLoop() {
                    GameObject closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
                    if (closedcurtain.inViewport() || closedcurtain.valid()) {
                        System.out.println("Closing curtain...");
                        closedcurtain.interact("Open");
                        Condition.wait(() -> !closedcurtain.valid() && Players.local().animation() == -1
                                && !Players.local().inMotion(), 250, 50);
                        Movement.walkTo(menaStairs);
                        Condition.wait(() -> Players.local().animation() == -1
                                && !Players.local().inMotion(), 250, 50);
                    }
                    GameObject stairsdownstairs = Objects.stream().within(Configs.downstairsMena).id(Configs.staircasedownstairs).nearest().first();
                    if (stairsdownstairs.valid()) {
                            System.out.println("firing inside");
                            stairsdownstairs.interact("Climb-up");
                            Condition.wait(() -> Configs.upstairs.contains(Players.local()), 250, 10);
                        cantKnock = false;
        }
        return 0;
    }
}