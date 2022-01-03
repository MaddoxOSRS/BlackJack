package io.maddox.behaviour.Luring.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class Lure extends Leaf {


    Npc bandittoLure;
    @Override
    public boolean isValid() {
        return !Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && Configs.zone.contains(Players.local())
                && Inventory.stream().id(Configs.WINE_ID).isNotEmpty();
    }

    @Override
    public int onLoop() {
        bandittoLure = Npcs.stream().within(Configs.missingThug).id(Configs.thug).nearest().first();
        if (!bandittoLure.inViewport()) {
            Camera.turnTo(bandittoLure);
        }
        Movement.running(false);
        if (Npcs.stream().interactingWithMe().isEmpty() && bandittoLure.interact("Lure")) {
            Condition.wait(() -> Widgets.widget(217).component(5).visible() || Chat.chatting(), 300, 50);
        }
        if (Widgets.widget(217).component(5).visible() || Chat.chatting()) {
            if (Chat.canContinue() && Chat.clickContinue()) {
            Condition.wait(() -> Widgets.widget(231).component(5).visible(), 250, 100);
            if (Condition.wait(() -> Widgets.widget(231).component(5).visible(), 250, 100)) {
                if (Widgets.widget(231).component(5).text().contains("I'm busy")) {
                    return 0;
                }
                if (Widgets.widget(231).component(5).text().contains("What is it")) {
                    if (Chat.canContinue() && Chat.clickContinue()) {
                        Condition.wait(() -> Widgets.widget(217).component(5).text().contains("Follow me"), 250, 100);
                    }
                    if (Widgets.widget(217).component(5).text().contains("Follow me") && Chat.canContinue() && Chat.clickContinue()) {
                        Condition.wait(() -> !Widgets.widget(217).component(5).visible(), 250, 150);
                        if (!Widgets.widget(217).component(5).visible()) {
                            Movement.running(false);
                            Movement.step(Configs.movement);
                            Condition.wait(() -> Players.local().animation() == -1
                                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
                            Movement.step(Configs.lure);
                            Condition.wait(() -> Configs.missingThug.contains(Players.local()), 50, 75);
                        }
                }
            }
                }
            }

        }
        return 0;
    }
}
