package io.maddox.behaviour.Restocking.Leaves;

import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.inMarket;
import static io.maddox.data.Configs.*;
import static io.maddox.data.WidgetnComponentConstants.*;

public class UnnoteFood extends Leaf {
    Npc banknotemanager;
    Item notedfood;
    @Override
    public boolean isValid() {
        return !Inventory.isFull()
                && Areas.inMarket.contains(Players.local());
    }

    @Override
    public int onLoop() {
        notedfood = Inventory.stream().id(Configs.notedfood).first();
        banknotemanager = Npcs.stream().within(inMarket).id(Configs.noteManager).nearest().first();
        Movement.walkTo(Areas.noteManager);
        Camera.turnTo(banknotemanager);
        if (banknotemanager.valid()) {
            if (Game.tab(Game.Tab.INVENTORY)) {
                if (notedfood.interact("Use")
                        && banknotemanager.interact("Use",true)) {
                    Condition.wait(Chat::chatting, 100, 50);
                }
                if (Widgets.widget(bankNoteManagerWidget).component(banknotemanagerselectioncomponent).component(bankNotemanagerALLComponent).visible()) {
                    Widgets.widget(bankNoteManagerWidget).component(banknotemanagerselectioncomponent).component(bankNotemanagerALLComponent).click();
                    Condition.wait(() -> Inventory.isFull(), 100, 50);
                    cantKnock = false;
                }
            }
        }
        return 0;
    }
}
