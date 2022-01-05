package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.mobile.script.ScriptManager;

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
        bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().firstOrNull();
        if (bandit.animation() == 838 && bandit.interact("Pickpocket")) { //Bandit is knocked out and we've pickpocketted less than twice
            Condition.wait(() -> Configs.xp < Skills.experience(Constants.SKILLS_THIEVING)
                    || Chat.canContinue(), Random.nextInt(600, 750), 5);
            pickCount++;
            if (!Players.local().healthBarVisible()) {
                bandit.interact("Pickpocket");
                pickCount++;
                System.out.println("Pickpocketing...");
                Condition.wait(() -> Configs.xp < Skills.experience(Constants.SKILLS_THIEVING)
                        || Chat.canContinue(), Random.nextInt(600, 750), 5);
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