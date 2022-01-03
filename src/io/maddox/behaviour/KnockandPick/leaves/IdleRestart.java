package io.maddox.behaviour.KnockandPick.leaves;

import io.maddox.data.Configs;
import io.maddox.framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;

import static io.maddox.data.Configs.*;


public class IdleRestart extends Leaf {


    @Override
    public boolean isValid() {
        return Configs.house.contains(Npcs.stream().within(Configs.house).id(Configs.thug).nearest().first())
                && !Inventory.stream().id(Configs.WINE_ID).isEmpty() && Configs.house.contains(Players.local())
                && Players.local().healthPercent() > Configs.toEat;
    }

    @Override
    public int onLoop() {
                if (Skill.Thieving.experience() > lastXPDrop) {
                    lastXPDrop = Skill.Thieving.experience();
                    timeIdle = System.currentTimeMillis();
                }
                if ((Configs.timeFromMark(timeIdle)) >= 10000) {
                    System.out.println("Idle for 10 seconds, restarting leaf");
                    pickCount = 0;
                    knockCount = 0;
                }
        return 0;
    }
}