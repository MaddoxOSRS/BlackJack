package io.maddox;

import com.google.common.eventbus.Subscribe;
import io.maddox.behaviour.EscapeCombatNorth.ActivateEscape;
import io.maddox.behaviour.EscapeCombatNorth.Leaves.ClimbDown;
import io.maddox.behaviour.EscapeCombatNorth.Leaves.ClimbUp;
import io.maddox.behaviour.EscapeCombatSouth.ActivateEscapeSouth;
import io.maddox.behaviour.EscapeCombatSouth.Leaves.ClimbDownSouth;
import io.maddox.behaviour.EscapeCombatSouth.Leaves.EscapeSouth;
import io.maddox.behaviour.KnockandPick.ActivateKnockout;
import io.maddox.behaviour.KnockandPick.leaves.Eat;
import io.maddox.behaviour.KnockandPick.leaves.KnockandPick;
import io.maddox.behaviour.KnockandPick.leaves.OpenPouch;
import io.maddox.behaviour.Luring.ActivateLure;
import io.maddox.behaviour.Luring.leaves.Lure;
import io.maddox.behaviour.Luring.leaves.MoveintoHouse;
import io.maddox.behaviour.Luring.leaves.OpenCurtain;
import io.maddox.behaviour.Luring.leaves.OpentoEnterHouse;
import io.maddox.behaviour.RelocatetoNorth.ActivateMovetoBandit;
import io.maddox.behaviour.RelocatetoNorth.Leaves.MovetoBandit;
import io.maddox.behaviour.Restocking.ActivatetoRestock;
import io.maddox.behaviour.Restocking.Leaves.Restock;
import io.maddox.behaviour.Restocking.Leaves.SellEmptyjugs;
import io.maddox.behaviour.Restocking.Leaves.UnnotedWines;
import io.maddox.behaviour.fallback.FallbackLeaf;
import io.maddox.behaviour.firstrun.FirsRunBranch;
import io.maddox.behaviour.firstrun.Leaves.StartLeaf;
import io.maddox.behaviour.operatecurtains.ActivateCurtain;
import io.maddox.behaviour.operatecurtains.leaves.CloseCurtain;
import io.maddox.behaviour.operatecurtains.leaves.OperateCurtain;
import io.maddox.behaviour.timeout.TimeoutLeaf;
import io.maddox.data.Areas;
import io.maddox.data.Configs;
import io.maddox.framework.Tree;
import org.powbot.api.event.BreakEvent;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.event.SkillExpGainedEvent;
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
import static io.maddox.data.Configs.*;

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
                        description = "What Bandit do you want to use? 737 = Bandit (41), 739 = Bandit (56), 3550 = Menaphite thug(55)",
                        defaultValue = "735",
                        allowedValues = {"737", "735", "3550"},
                        optionType = OptionType.INTEGER
                )
        }
)


public class Main extends AbstractScript {

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("MaddBlackjack", "miles", "127.0.0.1:5565", true, false);
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
            Configs.zoneupstairs=NorthZoneupstairs;
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
            Configs.zoneupstairs=NorthZoneupstairs;
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
            Configs.zoneupstairs=SouthZoneupstairs;
            Configs.missingThug= menaphiteArea;
            Configs.curtain=menaCurtain;
            Configs.lure=menatoLure;
            Configs.escapeup=menaStairs;
            Configs.escapedown=menaUpStairstile;
            Configs.upstairs=menaUpstairs;
            Configs.downstairsMena=menaDownstairs;
        }
        Configs.timeIdle = System.currentTimeMillis();
        Paint p = new PaintBuilder()
                .addString("Branch:" , () -> Configs.currentBranch )
                .addString("Leaf:" , () -> Configs.currentLeaf )
                .trackSkill(Skill.Thieving)
                .trackInventoryItem(995)
                .x(30)
                .y(50)
                .build();
        addPaint(p);
        instantiateTree();
    }

    private void instantiateTree() {
        tree.addBranches(
                new TimeoutLeaf(),
            //    new ActivateWorldHop().addLeafs(new HopWorld()), /* in testing */
                new FirsRunBranch().addLeafs(new StartLeaf()),
                new ActivateCurtain().addLeafs(new OperateCurtain(), new CloseCurtain()),
                new ActivateEscape().addLeafs(new ClimbUp(), new ClimbDown()),
                new ActivateEscapeSouth().addLeafs(new EscapeSouth(), new ClimbDownSouth()),
                new ActivatetoRestock().addLeafs(new Restock(), new UnnotedWines(), new SellEmptyjugs()),
                new ActivateLure().addLeafs( new OpenCurtain(), new Lure(), new MoveintoHouse(), new OpentoEnterHouse()),
                new ActivateKnockout().addLeafs(new Eat(), new KnockandPick(), new OpenPouch()),
                new ActivateMovetoBandit().addLeafs(new MovetoBandit()),
                new FallbackLeaf());
    }

    @Override
    public void poll() {
        if(Areas.LUMBRIDGE.contains(Players.local())) {
            ScriptManager.INSTANCE.stop();
        }

        tree.onLoop();
    }

    @Subscribe
    public static void onMessage(MessageEvent c) {
        String text = c.getMessage();
        if(text.contains("during combat")) {
            cantKnock = true;
        } else if (text.contains("You smack")) {
                cantKnock = false;
        }
    }

    @Subscribe
    public void expGained(SkillExpGainedEvent evt) {
        if (evt.getSkill().equals(Skill.Thieving)) {
            xp  = evt.getExpGained();
        }

    }


 @Subscribe
    public void onBreak(BreakEvent e) {
        if(upstairs.contains(Players.local())) {
            e.delay(10000);
        }
    }
}
