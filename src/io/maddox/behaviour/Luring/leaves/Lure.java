package io.maddox.behaviour.Luring.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;


public class Lure extends Leaf {

    Npc bandit;

    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.missingThug).id(Configs.thug).nearest().first();
        return !Configs.house.contains(bandit) && Configs.zone.contains(Players.local()) && !Inventory.stream().id(1993).isEmpty();
    }

    @Override
    public int onLoop() {
        GameObject closedcurtain = Objects.stream().within(Configs.house).action("Open").name("Curtain").nearest().first();
        Movement.running(false);
        if (closedcurtain.inViewport()
                && !Configs.house.contains(bandit)
                || closedcurtain.inViewport()
                && Configs.house.contains(Players.local())
                && !Configs.house.contains(bandit)) {
            closedcurtain.interact("Open");
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
        }
        if (!bandit.inViewport()) {
            Camera.turnTo(bandit);
        }
        if (!Npcs.stream().interactingWithMe().first().valid() && bandit.interact("Lure")) {
            System.out.println("Luring...");
            Condition.wait(() -> Chat.chatting(), 300, 50);
            if ((Widgets.widget(217).component(5).text().contains("I want to show you something") && Widgets.widget(217).component(5).visible())) {
                Chat.clickContinue();
                Condition.wait(() -> Widgets.widget(231).component(5).text().contains("What is it") && Widgets.widget(231).component(5).visible(), 250, 100);
                if (Condition.wait(() -> Widgets.widget(231).component(5).visible(), 250, 100)
                        && Widgets.widget(231).component(5).text().contains("What is it")) {
                    Chat.clickContinue();
                    Condition.wait(() -> Widgets.widget(217).component(5).text().contains("Follow me")
                            && Widgets.widget(217).component(5).visible(), 250, 100);
                } else if (Condition.wait(() -> Widgets.widget(231).component(5).visible(), 250, 100)
                        && Widgets.widget(231).component(5).text().contains("I'm busy")) {
                    bandit.interact("Lure");
                }
                    Chat.clickContinue();
                if (Widgets.widget(217).component(5).text().contains("Follow me") && Widgets.widget(217).component(5).visible()) {
                    Chat.clickContinue();
                    Condition.sleep(Random.nextInt(1152, 1757));
                    Movement.step(Configs.movement);
                    Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), 250, 5);
                    Movement.step(Configs.lure);
                    Condition.wait(() -> Configs.missingThug.contains(Players.local()), 50, 75);
                }
                else bandit.interact("Lure");
            }

        }
        return 0;
    }
}
