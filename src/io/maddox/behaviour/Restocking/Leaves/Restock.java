package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;

public class Restock extends Leaf {
    Npc banknotemanager;
    Npc generalstore;

    @Override
    public boolean isValid() {
        return Inventory.stream().id(1993).isEmpty();
    }

    @Override
    public int onLoop() {
        banknotemanager = Npcs.stream().within(7).id(1615).nearest().first();
        generalstore = Npcs.stream().within(7).id(3537).nearest().first();
        GameObject closedcurtain = Objects.stream().within(DYEHOUSE).id(1533).nearest().first();
        Item notedWines = Inventory.stream().id(1994).first();
        if (closedcurtain.inViewport()) {
            closedcurtain.interact("Open");
            Condition.sleep(Random.nextInt(1152, 1757));
        }
        if (!banknotemanager.valid()) {
            Movement.step(NoteManager);
        }
        if (generalstore.inViewport()) {
            generalstore.interact("Trade");
            Condition.wait(() -> Widgets.widget(300).component(16).visible(), 250, 150);
            if(Widgets.widget(300).component(16).visible()) {
                Inventory.stream().name("Jug").first().interact("Sell 50");
                Condition.wait(() -> Inventory.stream().name("Jug").isEmpty(), 250, 150);
                if (Condition.wait(() -> Inventory.stream().name("Jug").isEmpty(), 250, 150)) {
                    Widgets.widget(300).component(1).component(11).click();
                }
            }
            if (!Condition.wait(() -> Widgets.widget(300).component(16).visible(), 250, 150)) {
            if (banknotemanager.inViewport()) {
                if (Game.tab(Game.Tab.INVENTORY)) {
                    if (Inventory.selectedItem().id() == -1) {
                        notedWines.interact("Use");
                        Condition.wait(() -> Inventory.selectedItem() != Item.getNil(), 100, 3);
                        banknotemanager.interact("Use");
                        Condition.wait(() -> Widgets.widget(219).component(1).component(3).visible(), 250, 150);
                    }
                }
                    if (Widgets.widget(219).component(1).component(3).visible()) {
                        Widgets.widget(219).component(1).component(3).click();
                        Condition.wait(() -> !Inventory.stream().id(1993).isEmpty(), 250, 150);
                    }
                }
            }
        }
        return 0;
    }
}