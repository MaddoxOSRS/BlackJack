package io.maddox.behaviour.EscapeCombatSouth.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Areas.SouthZoneupstairs;
import static io.maddox.data.Configs.staircaseupstairs;


public class ClimbDownSouth extends Leaf {

    @Override
    public boolean isValid() {
        return !Configs.timetoJet() && Configs.upstairs.contains(Players.local()) && Configs.zoneupstairs == SouthZoneupstairs;
    }
    @Override
    public int onLoop() {
            GameObject stairsupstairs = Objects.stream().within(Configs.upstairs).id(staircaseupstairs).nearest().first();
            if (stairsupstairs.valid()) {
                if (stairsupstairs.interact("Climb-down")) {
                    System.out.println("South Climbing Down");
                    Condition.wait(() -> Configs.downstairs.valid(), 250, 10);
                }
        }
        return 0;
    }
}