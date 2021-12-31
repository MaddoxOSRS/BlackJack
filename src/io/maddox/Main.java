package io.maddox;

import io.maddox.behaviour.Eating.ActivateEat;
import io.maddox.behaviour.Eating.Leaves.Eat;
import io.maddox.behaviour.EscapeCombat.ActivateEscape;
import io.maddox.behaviour.EscapeCombat.Leaves.Escape;
import io.maddox.behaviour.Luring.ActivateLure;
import io.maddox.behaviour.Luring.leaves.Lure;
import io.maddox.behaviour.RelocatetoNorth.ActivateMovetoNorth;
import io.maddox.behaviour.RelocatetoNorth.Leaves.MovetoNorth;
import io.maddox.behaviour.Restocking.ActivatetoRestock;
import io.maddox.behaviour.Restocking.Leaves.Restock;
import io.maddox.behaviour.fallback.FallbackLeaf;
import io.maddox.behaviour.firstrun.FirsRunBranch;
import io.maddox.behaviour.firstrun.Leaves.StartLeaf;
import io.maddox.behaviour.timeout.TimeoutLeaf;
import io.maddox.behaviour.KnockandPick.ActivateKnockout;
import io.maddox.behaviour.KnockandPick.leaves.Pickpocket;
import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Tree;
import org.powbot.api.Color;
import org.powbot.api.event.BreakEvent;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

/**
 * Credits to @Proto && @Dan for Guidance, and information, Credits to Powbot Development Discord Section
 */



@ScriptManifest(
        name = "MaddBlackjack",
        description = "Does Blackjacking.",
        version = "0.01",
        author = "Maddox"
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name =  "Percentage to Eat",
                        description = "Percentage to nom on food - Default is 50",
                        optionType = OptionType.INTEGER,
                        defaultValue = "50"
                ),
        }
)


public class Main extends AbstractScript {

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("MaddBlackjack", "main", "127.0.0.1:5555", true, false);
    }
    private final Tree tree = new Tree();

    @Override
    public void onStart() {
        int nomming = getOption("Percentage to Eat");
        Configs.toEat = nomming;

        Paint p = new PaintBuilder()
                .addString("Branch:" , () -> Configs.currentBranch )
                .addString("Leaf:" , () -> Configs.currentLeaf )
                .trackSkill(Skill.Thieving)
                .trackInventoryItem(995)
                .backgroundColor(Color.argb(186, 16, 186, 0))
                .x(30)
                .y(50)
                .build();
        addPaint(p);
        instantiateTree();
    }

    private void instantiateTree() {
        tree.addBranches(
                new TimeoutLeaf(),
                new FirsRunBranch().addLeafs(new StartLeaf()),
                new ActivateEat().addLeafs(new Eat()),
                new ActivateLure().addLeafs(new Lure()),
                new ActivateKnockout().addLeafs(new Pickpocket()),
                new ActivateEscape().addLeafs(new Escape()),
               new ActivatetoRestock().addLeafs(new Restock()),
               new ActivateMovetoNorth().addLeafs(new MovetoNorth()),
               new FallbackLeaf()
        );
    }

    @Override
    public void poll() {
        if(Areas.LUMBRIDGE.contains(Players.local())) {
            ScriptManager.INSTANCE.stop();
        }

        tree.onLoop();
    }

    public void onBreak(BreakEvent e) {
        if(Areas.DYEHOUSE.contains(Players.local())) {
            e.delay(10000);
        }
    }
}
