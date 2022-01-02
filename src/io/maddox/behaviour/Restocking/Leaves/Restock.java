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
        if(closedcurtain.inViewport() && closedcurtain.interact("Open")) {
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), 350, 75);
        }
        generalstore = Npcs.stream().within(7).id(Configs.generalStore).nearest().first();
        if (!generalstore.valid()) {
            Movement.running(true);
            Movement.step(NoteManager);
        }
        generalstore = Npcs.stream().within(7).id(Configs.generalStore).nearest().first();
        if (generalstore.inViewport()) {
            generalstore.interact("Trade");
            Condition.wait(() -> Widgets.widget(300).component(16).visible(), 250, 150);
            if(Widgets.widget(300).component(16).visible()) {
                Inventory.stream().name(jug).first().interact("Sell 50");
                Condition.wait(() -> Inventory.stream().name(jug).isEmpty(), 250, 150);
                if (Condition.wait(() -> Inventory.stream().name(jug).isEmpty(), 250, 150)) {
                    Widgets.widget(300).component(1).component(11).click();
                }
            }
            banknotemanager = Npcs.stream().within(7).id(Configs.noteManager).nearest().first();
            if (Condition.wait(() -> !Widgets.widget(300).component(16).visible(), 250, 150)) {
                if (banknotemanager.inViewport()) {
                    if (Game.tab(Game.Tab.INVENTORY)) {
                        if (!notedWines.interact("Use")) return 0;
                        Condition.wait(() -> Inventory.selectedItemIndex() == notedWines.inventoryIndex(), 500, 3);
                        if (Inventory.selectedItemIndex() != notedWines.inventoryIndex()) return 0;
                            Condition.wait(() -> Inventory.selectedItem() != Item.getNil(), 100, 3);
                        if (!banknotemanager.interact("Use")) return 0;
                            Condition.wait(() -> Widgets.widget(219).component(1).component(3).visible(), 250, 150);
                        }
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
