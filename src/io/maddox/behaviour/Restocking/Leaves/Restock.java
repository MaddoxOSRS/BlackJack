package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.NoteManager;

public class Restock extends Leaf {
    GameObject closedcurtain;
    @Override
    public boolean isValid() {
        return  !Areas.inMarket.contains(Players.local());
    }

    @Override
    public int onLoop() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
       Camera.turnTo(closedcurtain);
        if (closedcurtain.inViewport() && closedcurtain.interact("Open")) {
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion() && !closedcurtain.valid(), 350, 75);
        }
        if (!Npcs.stream().within(7).id(Configs.generalStore).nearest().first().valid()) {
            Movement.running(true);
            Movement.step(NoteManager);
        }
        return 0;
    }
}