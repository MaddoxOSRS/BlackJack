package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.NoteManager;
import static io.maddox.data.Configs.jug;

public class Restock extends Leaf {
    Npc banknotemanager;
    Npc generalstore;
    GameObject closedcurtain;

    @Override
    public boolean isValid() {
        return Inventory.stream().id(Configs.WINE_ID).isEmpty();
    }

    @Override
    public int onLoop() {
        Item notedWines = Inventory.stream().id(Configs.NOTED_WINE_ID).first();
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();
        if (closedcurtain.inViewport() && closedcurtain.interact("Open")) {
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), 350, 75);
        }
        banknotemanager = Npcs.stream().within(7).id(Configs.noteManager).nearest().first();

        if (!banknotemanager.valid()) {
            Movement.running(true);
            Movement.step(NoteManager);
        }
        if (banknotemanager.inViewport()) {
                if (Game.tab(Game.Tab.INVENTORY)) {
                    if (notedWines.interact("Use") && banknotemanager.interact("Use")) {
                        Condition.wait(Chat::chatting, 250, 150);
                    }
                    if (Widgets.widget(219).component(1).component(3).visible()) {
                        Widgets.widget(219).component(1).component(3).click();
                        Condition.wait(() -> !Inventory.stream().id(Configs.WINE_ID).isEmpty(), 250, 150);
                }
            }
        }
        return 0;
    }
}