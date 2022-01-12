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
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().first())
                && !Inventory.stream().name(Configs.food).isEmpty() && Configs.house.contains(Players.local());
    }

    @Override
    public int onLoop() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().firstOrNull();
        Camera.turnTo(bandit);
         if (bandit != null && bandit.interact("Knock-Out")) {
                out.println("Knocking out...");
                Condition.wait(() -> xp < Constants.SKILLS_THIEVING ||
                        Players.local().animation() == 401, 200, 20);
            }
        if (Configs.ohshit()) {
            System.out.println("Yeeted for the ohshit");
            if (bandit != null && bandit.interact("Knock-Out")) {
                out.println("Knocking out...");
                Condition.wait(() -> xp < Constants.SKILLS_THIEVING ||
                        Players.local().animation() == 401, 220, 20);
            }
        }
        bandit = Npcs.stream().within(Configs.house).id(Configs.thugID).nearest().firstOrNull();
                if (bandit != null && bandit.interact("Pickpocket")) { //Bandit is knocked out, and we've pickpocketed less than twice
                    Condition.wait(() -> xp < Constants.SKILLS_THIEVING || Players.local().spotAnimation() < 56, 250, 50);
                    if (Condition.wait(() -> xp < Constants.SKILLS_THIEVING || Players.local().spotAnimation() < 56, 250, 50)) {
                        bandit.interact("Pickpocket");
                        System.out.println("Pickpocketing...");
                        Condition.wait(() -> xp < Constants.SKILLS_THIEVING || Players.local().spotAnimation() < 56, 500, 50);
                    }
                }
            return 0;
        }
}