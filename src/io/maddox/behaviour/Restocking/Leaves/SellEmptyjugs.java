package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.inMarket;
import static io.maddox.data.Configs.*;

public class SellEmptyjugs extends Leaf {

    Npc generalstore;

    @Override
    public boolean isValid() {
        return Inventory.stream().name(jug).isNotEmpty() && inMarket.contains(Players.local());
    }

    @Override
    public int onLoop() {
        generalstore = Npcs.stream().within(inMarket).id(Configs.generalStore).nearest().first();
        if (generalstore.valid()) {
            generalstore.interact("Trade");
            Condition.wait(() -> Widgets.widget(generalstorewindowWidget).component(generalstoremainComponent).visible(), 250, 150);
            if (Widgets.widget(generalstorewindowWidget).component(generalstoremainComponent).visible()) {
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
