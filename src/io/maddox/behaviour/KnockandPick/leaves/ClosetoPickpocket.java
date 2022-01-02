package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;


public class ClosetoPickpocket extends Leaf {

    GameObject openedcurtain;
    @Override
    public boolean isValid() {
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first()) && Configs.house.contains(Players.local()) &&
                Objects.stream().at(Configs.curtain).id(Configs.openCurtain).nearest().first().valid();
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
        return 0;
    }
}