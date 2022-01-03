package io.maddox.behaviour.Eating;

import io.maddox.data.Configs;
import io.maddox.framework.Branch;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

import static io.maddox.data.Configs.jug;


public class InteractwithJugandPouch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.stream().name(Configs.moneyPouch).isNotEmpty() || Inventory.stream().name(Configs.jug).count() > 0;
    }
}
