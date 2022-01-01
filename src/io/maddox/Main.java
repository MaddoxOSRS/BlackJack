package io.maddox;

import io.maddox.behaviour.Eating.ActivateEat;
import io.maddox.behaviour.Eating.Leaves.DropJug;
import io.maddox.behaviour.Eating.Leaves.Eat;
import io.maddox.behaviour.Eating.Leaves.OpenMoneyPouch;
import io.maddox.behaviour.EscapeCombat.ActivateEscape;
import io.maddox.behaviour.EscapeCombat.Leaves.ClimbDown;
import io.maddox.behaviour.EscapeCombat.Leaves.ClimbUp;
import io.maddox.behaviour.KnockandPick.ActivateKnockout;
import io.maddox.behaviour.KnockandPick.leaves.*;
import io.maddox.behaviour.Luring.ActivateLure;
import io.maddox.behaviour.Luring.leaves.*;
import io.maddox.behaviour.RelocatetoNorth.ActivateMovetoBandit;
import io.maddox.behaviour.RelocatetoNorth.Leaves.MovetoBandit;
import io.maddox.behaviour.Restocking.ActivatetoRestock;
import io.maddox.behaviour.Restocking.Leaves.Restock;
import io.maddox.behaviour.fallback.FallbackLeaf;
import io.maddox.behaviour.firstrun.FirsRunBranch;
import io.maddox.behaviour.firstrun.Leaves.StartLeaf;
import io.maddox.behaviour.timeout.TimeoutLeaf;
import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Tree;
import org.powbot.api.Color;
import org.powbot.api.event.BreakEvent;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.OptionType;
import org.powbot.api.script.ScriptConfiguration;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import static io.maddox.data.Areas.*;

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
                @ScriptConfiguration(
                        name = "Select Bandit",
                        description = "What Bandit do you want to use? 737 = Bandit (41), 735 = Bandit (56), 3550 = Menaphite thug(55)",
                        defaultValue = "735",
                        allowedValues = {"737", "735", "3550"},
                        optionType = OptionType.INTEGER
                )
        }
)


public class Main extends AbstractScript {

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("MaddBlackjack", "excave", "127.0.0.1:5575", true, false);
    }
    private final Tree tree = new Tree();

    @Override
    public void onStart() {
        int nomming = getOption("Percentage to Eat");
        int bandit = getOption("Select Bandit");
        Configs.toEat = nomming;
        Configs.thug = bandit;
        if(bandit == 737){
            Configs.house=DYEHOUSE;
            Configs.movement= outsidebanHouse;
            Configs.zone=NorthZone;
            Configs.missingThug=DYEHOUSE2;
            Configs.curtain=Curtain;
            Configs.lure=dyetoLure;
            Configs.escapeup= dyeLadderdownstairs;
            Configs.escapedown= dyeLadderupstairs;
            Configs.upstairs=dyeupstairs;
            Configs.downstairs=dyedownstairs;
        }
        else if(bandit == 735){
           Configs.house=DYEHOUSE;
            Configs.movement= outsidebanHouse;
            Configs.zone=NorthZone;
            Configs.missingThug=DYEHOUSE2;
            Configs.curtain=Curtain;
            Configs.lure=dyetoLure;
            Configs.escapeup= dyeLadderdownstairs;
            Configs.escapedown= dyeLadderupstairs;
            Configs.upstairs=dyeupstairs;
            Configs.downstairs=dyedownstairs;
        }
        else if(bandit == 3550){
            Configs.house =menaphiteHouse;
            Configs.movement=outsideMenaHouse;
            Configs.zone=SouthZone;
            Configs.missingThug= menaphiteArea;
            Configs.curtain=menaCurtain;
            Configs.lure=menatoLure;
            Configs.escapeup=menaStairs;
            Configs.upstairs=menaUpstairs;
            Configs.downstairs=menaDownstairstile;
        }
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
                new ActivateEscape().addLeafs(new ClimbUp(), new ClimbDown()),
                new ActivateEat().addLeafs(new Eat(), new DropJug(), new OpenMoneyPouch()),
                new ActivatetoRestock().addLeafs(new Restock()),
                new ActivateLure().addLeafs( new OpenCurtain(), new Lure(), new ClosetoPickpocket(), new MoveintoHouse(), new OpentoEnterHouse()),
                new ActivateKnockout().addLeafs(new KnockandPick()),
               new ActivateMovetoBandit().addLeafs(new MovetoBandit()),
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
