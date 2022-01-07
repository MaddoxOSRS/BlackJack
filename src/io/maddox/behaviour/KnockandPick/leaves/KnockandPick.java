package io.maddox.behaviour.KnockandPick.leaves;

import com.google.common.eventbus.Subscribe;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.knockCount;
import static io.maddox.data.Configs.pickCount;
import static java.lang.System.out;



public class KnockandPick extends Leaf {

    Npc bandit;

    @Override
    public boolean isValid() {
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(Configs.WINE_ID).isEmpty() && Configs.house.contains(Players.local()) && Players.local().healthPercent() > Configs.toEat;
    }

    @Override
    public int onLoop() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().firstOrNull();
         if (bandit != null && bandit.interact("Knock-Out")) {
                out.println("Knocking out...");
             pickCount = 0;
                Condition.wait(() ->
                        Players.local().animation() == 401, 350, 5);
             knockCount++;
            }
        if (Configs.ohshit()) {
            System.out.println("Yeeted for the ohshit");
            knockCount = 0;
            return 0;
        }
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().firstOrNull();
            if (knockCount >= 1 && pickCount <= 2) {
                if (bandit.interact("Pickpocket")) { //Bandit is knocked out, and we've pickpocketed less than twice
                    pickCount++;
                    Condition.sleep((570));
                    bandit.interact("Pickpocket");
                    pickCount++;
                    knockCount = 0;
                    System.out.println("Pickpocketing...");
                    Condition.wait(() -> bandit.animation() <= 808, 425, 5);
            }
        }
            return 0;
        }
}