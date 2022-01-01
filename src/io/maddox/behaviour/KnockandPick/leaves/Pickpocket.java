package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;



public class Pickpocket extends Leaf {

    Npc bandit;
    Npc attackingBandit;
    boolean killMessage;
    @Override
    public boolean isValid() {
        killMessage = Npcs.stream().filter(npc -> npc.overheadMessage() == "I'll kill you for that!").isEmpty();
        attackingBandit = Npcs.stream().id(Configs.thug).filter(npc -> npc.interacting().valid()).nearest().first();
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return killMessage && Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(1993).isEmpty();
    }

    @Override
    public int onLoop() {
        GameObject openedcurtain = Objects.stream().at(Configs.curtain).id(1534).nearest().first();
        GameObject closedcurtain = Objects.stream().at(Configs.curtain).id(1533).nearest().first();
        if (closedcurtain.inViewport() && Configs.house.contains(bandit) && !Configs.house.contains(Players.local())) {
            closedcurtain.interact("Open");
            Condition.wait(() -> Players.local().animation() == -1
                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
        }
        if (!Configs.house.contains(Players.local()) && Configs.house.contains(bandit)) {
            Movement.moveTo(Configs.lure);
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), 250, 5);
        }
        if (Configs.house.contains(Players.local()) && Configs.house.contains(bandit)) {
            if (openedcurtain.inViewport()) {
                System.out.println("Closing curtain...");
                openedcurtain.interact("Close");
                Condition.wait(() -> Players.local().animation() == -1
                        && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
            }
            String jug = "Jug";
            if (Inventory.stream().name(jug).count() > 0) {
                Item emptyJug = Inventory.stream().name(jug).first();
                emptyJug.interact("Drop");
            }
            String pouchName = "Coin pouch";
            if (Inventory.stream().name(pouchName).count() == 28 ) {
                Item pouch = Inventory.stream().name(pouchName).first();
                pouch.interact("Open-all");
            }
            if (!bandit.inViewport()) {
                Camera.turnTo(bandit);
            }
            if (bandit.animation() <= 808) {
                if (bandit.interact("Knock-Out")) {
                    System.out.println("Knocking out...");
                    Condition.wait(() -> Players.local().animation() == 401 || Chat.canContinue(), Random.nextInt(150,175), 5);
                }
            }
            if (Condition.wait(() -> Players.local().animation() == 401, Random.nextInt(150,175), 5)) {
                System.out.println("Pickpocketing...");
                bandit.interact("Pickpocket");
                Condition.sleep(Random.nextInt(475, 525));
                bandit.interact("Pickpocket");
                Condition.wait(() -> Players.local().animation() == 827 || Chat.canContinue(), 5, 1);
            }
        }
        return 0;
    }
}

