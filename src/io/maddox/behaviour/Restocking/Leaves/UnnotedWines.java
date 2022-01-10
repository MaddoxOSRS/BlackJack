package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.cantKnock;
import static io.maddox.data.Configs.knockCount;

public class UnnotedWines extends Leaf {
    Npc banknotemanager;

    @Override
    public boolean isValid() {
        return Inventory.stream().id(Configs.WINE_ID).isEmpty() && Areas.inMarket.contains(Players.local());
    }

    int bankNoteManagerWidget = 219;
    int banknotemanagerselectioncomponent = 1;
    int bankNotemanagerALLComponent = 3;

    @Override
    public int onLoop() {
        Item notedWines = Inventory.stream().id(Configs.NOTED_WINE_ID).first();
        banknotemanager = Npcs.stream().within(7).id(Configs.noteManager).nearest().first();
        if (banknotemanager.inViewport()) {
            if (Game.tab(Game.Tab.INVENTORY)) {
                if (notedWines.interact("Use") && banknotemanager.interact("Use")) {
                    Condition.wait(Chat::chatting, 250, 150);
                }
                if (Widgets.widget(bankNoteManagerWidget).component(banknotemanagerselectioncomponent).component(bankNotemanagerALLComponent).visible()) {
                    Widgets.widget(bankNoteManagerWidget).component(banknotemanagerselectioncomponent).component(bankNotemanagerALLComponent).click();
                    Condition.wait(() -> !Inventory.stream().id(Configs.WINE_ID).isEmpty(), 250, 150);
                    cantKnock = false;
                    knockCount = 0;
                }
            }
        }
        return 0;
    }
}
