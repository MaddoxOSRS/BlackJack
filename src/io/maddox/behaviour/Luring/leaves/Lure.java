package io.maddox.behaviour.Luring.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;



public class Lure extends Leaf {


    Npc bandittoLure;
    GameObject closedcurtain;
    @Override
    public boolean isValid() {
        closedcurtain = Objects.stream().at(Configs.curtain).id(Configs.closedCurtain).nearest().first();

        return !Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first())
                && Configs.zone.contains(Players.local()) && !closedcurtain.valid()
                && Inventory.stream().name(Configs.food).action("Eat").isNotEmpty()
                || !Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first())
                && Configs.zone.contains(Players.local()) && !closedcurtain.valid()
                && Inventory.stream().name(Configs.food).action("Drink").isNotEmpty();
    }

    int banditLureWidget = 231;
    int banditLureComponent = 5;
    int playerlureWidget = 217;
    int playerlureComponent = 5;

    String banditbusy = "I'm busy";
    String optionLure = "Lure";
    String banditWhatisit = "What is it";
    String playerFollowme = "Follow me";

    @Override
    public int onLoop() {
        bandittoLure = Npcs.stream().within(Configs.missingThug).id(Configs.thugID).nearest().first();
        if (!bandittoLure.inViewport()) {
            Camera.turnTo(bandittoLure);
        }
        Movement.running(false);
            if (!Configs.amfollowing() && bandittoLure.interact(optionLure)) {
                Condition.wait(() -> Widgets.widget(playerlureWidget).component(playerlureComponent).visible()
                        || Chat.chatting(), 300, 50);
                if (Widgets.widget(playerlureWidget).component(playerlureComponent).visible()
                        || Chat.chatting()) {
                    if (Chat.canContinue() && Chat.clickContinue()) {
                        Condition.wait(() -> Widgets.widget(banditLureWidget).component(banditLureComponent).visible(), 250, 100);
                        if (Condition.wait(() -> Widgets.widget(banditLureWidget).component(banditLureComponent).visible(), 250, 100)) {
                            if (Widgets.widget(banditLureWidget).component(banditLureComponent).text().contains(banditbusy)) {
                                Chat.clickContinue();
                                Movement.step(Configs.movement);
                                return 0;
                            } else {
                                if (Widgets.widget(banditLureWidget).component(banditLureComponent).text().contains(banditWhatisit)) {
                                    if (Chat.canContinue() && Chat.clickContinue()) {
                                        Condition.wait(() -> Widgets.widget(playerlureWidget).component(5).text().contains(playerFollowme), 250, 100);
                                    }
                                    if (Widgets.widget(playerlureWidget).component(playerlureComponent).text().contains(playerFollowme) && Chat.canContinue() && Chat.clickContinue()) {
                                        Condition.wait(() -> !Widgets.widget(playerlureWidget).component(playerlureComponent).visible(), 250, 150);
                                        if (!Widgets.widget(playerlureWidget).component(playerlureComponent).visible()) {
                                            Movement.running(false);
                                            Movement.step(Configs.movement);
                                            Condition.wait(() -> Players.local().animation() == -1
                                                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
                                            Movement.step(Configs.lure);
                                            Condition.wait(() -> Configs.house.contains(Players.local()), 50, 75);
                                        }
                                    }
                            }
                        }

                    }
                }
            }
        }
        return 0;
    }
}

