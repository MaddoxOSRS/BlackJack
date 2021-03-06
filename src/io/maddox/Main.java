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
import io.maddox.behaviour.Luring.ActivateLure;
import io.maddox.behaviour.Luring.leaves.Lure;
import io.maddox.behaviour.Luring.leaves.MoveintoHouse;
import io.maddox.behaviour.Luring.leaves.OpentoEnterHouse;
import io.maddox.behaviour.KnockandPick.leaves.MovetoBandit;
import io.maddox.behaviour.Restocking.ActivatetoRestock;
import io.maddox.behaviour.Restocking.Leaves.Restock;
import io.maddox.behaviour.Restocking.Leaves.SellEmptyjugs;
import io.maddox.behaviour.Restocking.Leaves.UnnoteFood;
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
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
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
                        name =  "What percentage would you like to eat?",
                        description = "Percentage to nom on food - Default is 50",
                        optionType = OptionType.INTEGER,
                        defaultValue = "50"
                ),
                @ScriptConfiguration(
                        name = "Select Bandit",
                        description = "What Bandit do you want to BlackJack?",
                        defaultValue = "Bandit(41)",
                        allowedValues = {"Bandit(41)", "Bandit(56)", "Menaphite thug(55)"},
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name = "Select Food to use",
                        description = "What Food would you like to eat?",
                        defaultValue = "Jug of Wine",
                        allowedValues = {"Jug of Wine", "Cake", "Shark"},
                        optionType = OptionType.STRING
                )
        }
)


public class Main extends AbstractScript {

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("MaddBlackjack", "maddox", "127.0.0.1:5585", true, false);
    }
    private final Tree tree = new Tree();

    @Override
    public void onStart() {
        int nomming = getOption("What percentage would you like to eat?");
        String bandit = getOption("Select Bandit");
        String foodtouse = getOption("Select Food to use");
        toEat = nomming;
        thug = bandit;
        food = foodtouse;
        if (food == "Shark"){
            notedfood = 386;
            foodAction = Inventory.stream().filtered(i -> i.valid() && !i.stackable() && (i.actions().contains("Eat"))).first();
        }
        else if (food == "Jug of Wine"){
            notedfood = 1994;
            foodAction = Inventory.stream().filtered(i -> i.valid() && !i.stackable() && (i.actions().contains("Drink"))).first();

        }
        else if (food == "Cake"){
            notedfood = 1892;
            foodAction = Inventory.stream().filtered(i -> !i.stackable() && (i.actions().contains("Eat"))).first();

        }

        if(bandit == "Bandit(41)"){
            thugID = 737;
            house=DYEHOUSE;
            movement= outsidebanHouse;
            zone=NorthZone;
            zoneupstairs=NorthZoneupstairs;
            missingThug=DYEHOUSE2;
            curtain=Curtain;
            lure=dyetoLure;
            escapeup= dyeLadderdownstairs;
            escapedown= dyeLadderupstairs;
            upstairs=dyeupstairs;
            downstairs=dyedownstairs;
        }
        else if(bandit == "Bandit(56)"){
            thugID = 735;
           house=DYEHOUSE;
            movement= outsidebanHouse;
            zone=NorthZone;
            zoneupstairs=NorthZoneupstairs;
            missingThug=DYEHOUSE2;
            curtain=Curtain;
            lure=dyetoLure;
            escapeup= dyeLadderdownstairs;
            escapedown= dyeLadderupstairs;
            upstairs=dyeupstairs;
            downstairs=dyedownstairs;
        }
        else if(bandit == "Menaphite thug(55)"){
            thugID = 3550;
            house =menaphiteHouse;
            movement=outsideMenaHouse;
            zone=SouthZone;
            zoneupstairs=SouthZoneupstairs;
            missingThug= menaphiteArea;
            curtain=menaCurtain;
            lure=menatoLure;
            escapeup=menaStairs;
            escapedown=menaUpStairstile;
            upstairs=menaUpstairs;
            downstairsMena=menaDownstairs;
        }
        timeIdle = System.currentTimeMillis();
        Paint p = new PaintBuilder()
                .addString("Branch:" , () -> Configs.currentBranch )
                .addString("Leaf:" , () -> Configs.currentLeaf )
                .addString("Food selected:" , () -> food)
                .trackSkill(Skill.Thieving)
                .x(30)
                .y(50)
                .build();
        addPaint(p);
        instantiateTree();
    }

    private void instantiateTree() {
        tree.addBranches(
                new TimeoutLeaf(),
         //      new ActivateWorldHop().addLeafs(new HopWorld()), /* in testing */
                new FirsRunBranch().addLeafs(new StartLeaf()),
                new ActivateCurtain().addLeafs(new OperateCurtain(), new CloseCurtain()),
                new ActivateEscape().addLeafs(new ClimbUp(), new ClimbDown()),
                new ActivateEscapeSouth().addLeafs(new EscapeSouth(), new ClimbDownSouth()),
                new ActivatetoRestock().addLeafs(new Restock(), new UnnoteFood(), new SellEmptyjugs()),
                new ActivateLure().addLeafs(new Lure(), new MoveintoHouse(), new OpentoEnterHouse()),
                new ActivateKnockout().addLeafs(new Eat(), new KnockandPick(), new MovetoBandit()),
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
