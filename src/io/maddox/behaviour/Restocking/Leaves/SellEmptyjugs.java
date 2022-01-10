package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Areas;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.Widgets;

import static io.maddox.data.Configs.jug;

public class SellEmptyjugs extends Leaf {

    Npc generalstore;

    @Override
    public boolean isValid() {
        return !Inventory.stream().name(jug).isNotEmpty() && Areas.inMarket.contains(Players.local());
    }

    int generalstorewindowWidget = 300;
    int generalstoremainComponent = 1;
    int generatlstoreEXITcomponent = 11;
    int generalstoreNameComponent = 16;

    @Override
    public int onLoop() {
        if (generalstore.inViewport()) {
            generalstore.interact("Trade");
            Condition.wait(() -> Widgets.widget(generalstorewindowWidget).component(generalstoreNameComponent).visible(), 250, 150);
            if (Widgets.widget(generalstorewindowWidget).component(generalstoreNameComponent).visible()) {
                Inventory.stream().name(jug).first().interact("Sell 50");
                Condition.wait(() -> Inventory.stream().name(jug).isEmpty(), 250, 150);
                if (Condition.wait(() -> Inventory.stream().name(jug).isEmpty(), 250, 150)) {
                    Widgets.widget(generalstorewindowWidget).component(generalstoremainComponent).component(generatlstoreEXITcomponent).click();
                }
            }
        }
        return 0;
    }
}
