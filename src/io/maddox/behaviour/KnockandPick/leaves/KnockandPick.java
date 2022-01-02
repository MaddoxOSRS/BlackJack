package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class KnockandPick extends Leaf {

    Npc bandit;
GameObject openedcurtain;
    @Override
    public boolean isValid() {
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(Configs.WINE_ID).isEmpty() && Configs.house.contains(Players.local());
    }

    @Override
    public int onLoop() {
        openedcurtain = Objects.stream().at(Configs.curtain).id(Configs.openCurtain).nearest().first();
        Movement.running(false);
        if (Npcs.stream().interactingWithMe().first().valid() && openedcurtain.valid()
                || !Npcs.stream().interactingWithMe().first().valid() && openedcurtain.valid()) {
            System.out.println("Closing curtain...");
            openedcurtain.interact("Close");
            Condition.wait(() -> Players.local().animation() == -1
                    && !Players.local().inMotion(), Random.nextInt(500, 750), 50);
        }
       bandit = Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first();
            if (!bandit.inViewport()) {
                Camera.turnTo(bandit);
            }
            if (bandit.animation() <= 808 && bandit.interact("Knock-Out")) {
                System.out.println("Knocking out...");
                Condition.wait(() -> Players.local().animation() == 401 || Chat.canContinue(), 25, 15);
            }
                int xp = Skills.experience(Constants.SKILLS_THIEVING);
                if (bandit.animation() == 838 && bandit.interact("Pickpocket")) {
                    System.out.println("Pickpocketing...");
                    Condition.wait(() -> xp > Skills.experience(Constants.SKILLS_THIEVING), 15, 3);
                    if (Condition.wait(() -> xp > Skills.experience(Constants.SKILLS_THIEVING), 15, 3)) {
                        bandit.interact("Pickpocket");
                        Condition.wait(() -> Players.local().animation() == 827 || Chat.canContinue(), 15, 3);
                    }
                }
                return 0;
            }
    }