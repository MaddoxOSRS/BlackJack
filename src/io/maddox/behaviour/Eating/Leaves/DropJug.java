package io.maddox.behaviour.Eating.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.jug;


public class DropJug extends Leaf {


    Item emptyJug;
    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.jug).count() > 0;
    }

    @Override
    public int onLoop() {
            emptyJug = Inventory.stream().name(jug).first();
           emptyJug.interact("Drop");
        return 0;
    }
}

