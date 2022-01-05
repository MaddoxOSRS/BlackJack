package io.maddox.behaviour.Eating.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.*;


public class DropJug extends Leaf {


    Item emptyJug;
    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.jug).count() > 0;
    }

    @Override
    public int onLoop() {
            emptyJug = Inventory.stream().name(jug).first();
          if(emptyJug.interact("Drop")) {
              knockCount = 0;
              pickCount = 0;
              Condition.wait(() -> Inventory.stream().name(Configs.jug).isEmpty(), 1000, 5);
          }

        return 0;
    }
}

