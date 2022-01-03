package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;

import static io.maddox.data.Configs.*;
import static java.lang.System.out;


public class PickPocket extends Leaf {

    Npc bandit;

    @Override
    public boolean isValid() {
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(Configs.WINE_ID).isEmpty() && Configs.house.contains(Players.local())
                && Players.local().healthPercent() > Configs.toEat && knockCount >= 1 && pickCount <= 2;
    }

    @Override
    public int onLoop() {

        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
        if (!bandit.inViewport()) {
            Camera.turnTo(bandit);
        }
        int xp = Skills.experience(Constants.SKILLS_THIEVING);

        if (bandit.animation() == 838 && pickCount < 2) { //Bandit is knocked out and we've pickpocketted less than twice
            if (bandit.interact("Pickpocket")) {
                System.out.println("Pickpocketing...");
                Condition.wait(() -> xp < Skill.Thieving.experience() || Chat.canContinue(), 20, 20);
                pickCount++;
                System.out.println("pick count: " + pickCount);
            }
            if (pickCount >= 2) {
                knockCount = 0;
                System.out.println("resetting knock count...");
                return 0;
            }
        }

        return 0;
    }
}