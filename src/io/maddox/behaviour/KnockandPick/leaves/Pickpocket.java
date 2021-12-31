package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.behaviour.EscapeCombat.ActivateEscape;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

import static io.maddox.data.Areas.*;


public class Pickpocket extends Leaf {

    Npc bandit;
    Npc attackingBandit;
    @Override
    public boolean isValid() {
        attackingBandit = Npcs.stream().id(735).filter(npc -> npc.interacting().valid()).nearest().first();
        bandit = Npcs.stream().within(DYEHOUSE).id(735).nearest().first();
        return DYEHOUSE.contains(Npcs.stream().within(2).id(735).nearest().first()) && !(bandit.animation() == 390) && DYEHOUSE.contains(Players.local()) && !Inventory.stream().id(1993).isEmpty();
    }

    @Override
    public int onLoop() {
        GameObject openedcurtain = Objects.stream().within(DYEHOUSE).action("Close").name("Curtain").nearest().first();
        GameObject closedcurtain = Objects.stream().within(DYEHOUSE).action("Open").name("Curtain").nearest().first();
        System.out.println("Luring Bandit.");
        if (closedcurtain.inViewport() && DYEHOUSE.contains(bandit) && !DYEHOUSE.contains(Players.local())) {
            closedcurtain.interact("Open");
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), 250, 5);
        }
        if (!DYEHOUSE.contains(Players.local()) && DYEHOUSE.contains(bandit)) {
            Movement.moveTo(toLure);
            Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), 250, 5);
        }
        if (DYEHOUSE.contains(Players.local()) && DYEHOUSE.contains(bandit)) {
            if (openedcurtain.inViewport()) {
                System.out.println("Closing curtain...");
                openedcurtain.interact("Close");
                Condition.wait(() -> Players.local().animation() == -1 && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
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
                    Condition.wait(() -> bandit.animation() >= 838, Random.nextInt(50,75), 5);
                }
            }
            if (Condition.wait(() -> bandit.animation() >= 838, Random.nextInt(50,75), 5)) {
                System.out.println("Pickpocketing...");
                bandit.interact("Pickpocket");
            }
        }
        return 0;
    }
}

