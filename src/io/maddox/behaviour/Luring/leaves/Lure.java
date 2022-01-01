package io.maddox.behaviour.Luring.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class Lure extends Leaf {

    Npc bandit;

    Npc bandittoLure;
    @Override
    public boolean isValid() {
        bandittoLure = Npcs.stream().within(Configs.missingThug).id(Configs.thug).nearest().first();
            bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return !Configs.house.contains(bandit) && Configs.zone.contains(Players.local()) && Inventory.stream().id(Configs.WINE_ID).isNotEmpty();
    }

    @Override
    public int onLoop() {
        if (!bandittoLure.inViewport()) {
            Camera.turnTo(bandit);
        }
        if (!Widgets.widget(217).component(5).visible() && !Chat.chatting() && bandittoLure.interact("Lure")) {
            Condition.wait(() -> Widgets.widget(217).component(5).visible() || Chat.chatting(), 150, 15);
        }
        if (Widgets.widget(217).component(5).visible() || Chat.chatting()) {
            if (Chat.canContinue() && Chat.clickContinue()) {
            Condition.wait(() -> Widgets.widget(231).component(5).text().contains("What is it") && Widgets.widget(231).component(5).visible(), 250, 100);
            if (Chat.canContinue() && Chat.clickContinue()) {
                Condition.wait(() -> Widgets.widget(217).component(5).text().contains("Follow me"), 250, 100);
            }
                if (Chat.canContinue() && Chat.clickContinue()) {
                    Condition.wait(() -> !Widgets.widget(217).component(5).visible(), 250, 150);
                    Movement.running(false);
                    Movement.step(Configs.movement);
                    Condition.wait(() -> Players.local().animation() == -1
                            && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
                    Movement.step(Configs.lure);
                    Condition.wait(() -> Configs.missingThug.contains(Players.local()), 50, 75);
                }
            }

        }
        return 0;
    }
}
