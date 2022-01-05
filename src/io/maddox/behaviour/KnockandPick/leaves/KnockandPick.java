package io.maddox.behaviour.KnockandPick.leaves;

import com.google.common.eventbus.Subscribe;
import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.rt4.*;

import static io.maddox.data.Configs.*;
import static java.lang.System.out;



public class KnockandPick extends Leaf {

    Npc bandit;
    GameObject openedcurtain;

    @Override
    public boolean isValid() {
        return house.contains(Npcs.stream().within(house).id(thug).nearest().first())
                && !Inventory.stream().id(WINE_ID).isEmpty() && house.contains(Players.local());
    }

    @Override
    public int onLoop() {
        openedcurtain = Objects.stream().at(curtain).id(openCurtain).nearest().first();
        Movement.running(false);
        if (Configs.inCombat() && openedcurtain.valid()
                || !Configs.inCombat() && openedcurtain.valid()) {
            out.println("Closing curtain...");
            openedcurtain.interact("Close");
            Condition.wait(() -> Players.local().animation() == -1
                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
        }
        int xp = Skills.experience(Constants.SKILLS_THIEVING);
            bandit = Npcs.stream().within(house).id(thug).nearest().firstOrNull();
        if (bandit != null && bandit.interact("Knock-Out")) {
            out.println("Knocking out...");
            Condition.wait(() ->
                    bandit.animation() == 838
                            || Chat.canContinue() || Players.local().animation() == -1
                            && !Players.local().inMotion(), Random.nextInt(700, 800), 2);
        }
        if (Configs.ohShit()) {
            out.println("firing ohshit");
            if (bandit != null && bandit.interact("Knock-Out")) {
                out.println("Knocking out...");
                Condition.wait(() ->
                        bandit.animation() == 838
                                || Chat.canContinue() || Players.local().animation() == -1
                                && !Players.local().inMotion(), Random.nextInt(700, 800), 2);
            }
        }
            if (bandit.animation() == 838 && bandit.interact("Pickpocket")) { //Bandit is knocked out and we've pickpocketted less than twice
                Condition.wait(() -> Configs.xp < Skills.experience(Constants.SKILLS_THIEVING)
                        || Chat.canContinue(), Random.nextInt(500, 600), 5);
                if (!Players.local().healthBarVisible()) {
                    bandit.interact("Pickpocket");
                    System.out.println("Pickpocketing...");
                    Condition.wait(() -> xp < Skills.experience(Constants.SKILLS_THIEVING)
                            || Chat.canContinue() || Players.local().animation() == -1
                            && !Players.local().inMotion(), Random.nextInt(750, 1000), 5);
                }
            }
        return 0;
    }
}