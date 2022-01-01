package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class Pickpocket extends Leaf {

    Npc bandit;
    @Override
    public boolean isValid() {
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        return bandit.animation() == 838 && Configs.house.contains(Players.local());
    }

    @Override
    public int onLoop() {
        int xp = Skills.experience(Constants.SKILLS_THIEVING);
        if (bandit.animation() == 838 && bandit.interact("Pickpocket")) {
            System.out.println("Pickpocketing...");
            Condition.wait(() -> xp > Skills.experience(Constants.SKILLS_THIEVING), 15, 3);
            if (Condition.wait(() -> xp > Skills.experience(Constants.SKILLS_THIEVING), 15, 3)) {
                bandit.interact("Pickpocket");
                Condition.wait(() -> bandit.animation() <= 808 || Chat.canContinue(), 15, 3);
            }
        }
        return 0;
    }
}

