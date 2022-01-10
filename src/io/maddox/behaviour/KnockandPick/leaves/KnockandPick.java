package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.*;
import static java.lang.System.out;



public class KnockandPick extends Leaf {

    Npc bandit;

    @Override
    public boolean isValid() {
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(Configs.food).isEmpty() && Configs.house.contains(Players.local());
    }

    @Override
    public int onLoop() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().firstOrNull();
         if (bandit != null && bandit.interact("Knock-Out")) {
                out.println("Knocking out...");
             pickCount = 0;
                Condition.wait(() ->
                        Players.local().animation() == 401, 200, 5);
             knockCount++;
            }
        if (Configs.ohshit()) {
            System.out.println("Yeeted for the ohshit");
            knockCount = 0;
            if (bandit != null && bandit.interact("Knock-Out")) {
                out.println("Knocking out...");
                pickCount = 0;
                knockCount++;
                Condition.wait(() ->
                        Players.local().animation() == 401, 200, 5);
            }
        }
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().firstOrNull();
            if (knockCount >= 1) {
                if (bandit.interact("Pickpocket")) { //Bandit is knocked out, and we've pickpocketed less than twice
                    pickCount++;
                    Condition.wait(() -> xp < Constants.SKILLS_THIEVING || Players.local().animation() == -1
                            && !Players.local().inMotion(), 450, 50);
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