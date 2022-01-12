package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.NoteManager;
import static io.maddox.data.Areas.inMarket;

public class Restock extends Leaf {
    GameObject closedcurtain;
    @Override
    public boolean isValid() {
        return !Areas.inMarket.contains(Players.local());
    }

    @Override
    public int onLoop() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
       Camera.turnTo(closedcurtain);
        if (closedcurtain.valid() && closedcurtain.interact("Open")) {
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion() && !closedcurtain.valid(), 50, 50);
        }
            Movement.running(true);
            Movement.step(NoteManager);
        return 0;
    }
}