package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;

import static io.maddox.data.Areas.NoteManager;
import static io.maddox.data.Configs.inCombat;

public class Restock extends Leaf {

    Npc generalstore;

    @Override
    public boolean isValid() {
        return Inventory.stream().id(Configs.WINE_ID).isEmpty() && !inCombat();
    }

    @Override
    public int onLoop() {
        generalstore = Npcs.stream().within(7).id(Configs.generalStore).nearest().first();
        if (!generalstore.valid()) {
            Movement.running(true);
            Movement.step(NoteManager);
        }
        return 0;
    }
}