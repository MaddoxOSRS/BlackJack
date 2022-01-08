package io.maddox.data;


import org.powbot.api.Area;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

import static java.lang.System.currentTimeMillis;

public class Configs {

    //Branch information
    public static String currentBranch = "";
    public static String currentLeaf = "";
    public static int loopReturn = 200;
    public static String status = "waiting";
    public static boolean started = false;
    public static boolean startRun = false;
    public static int thug;
    public static Area house;
    public static Tile movement;
    public static Tile curtain;
    public static Tile lure;
    public static Tile escapeup;
    public static Tile escapedown;
    public static Area zone;
    public static Area zoneupstairs;
    public static Area upstairs;
    public static Tile downstairs;
    public static Area downstairsMena;
    public static Area missingThug;

    //World Hop stuff
    public static long timeIdle = 0;
    public static long lastXPDrop = 0;
    public static int loggedintoWorld;
    public static int current_world = 1;
    public static int last_world = 2;

    public static boolean cantKnock = false;

    //thieve counter
    public static int pickCount;
    public static int knockCount = 0;

    public static final int[] mems = {302, 303, 304, 305, 306, 307, 309, 310, 311, 312, 313, 314, 315, 317, 318, 319, 320, 321, 322, 323, 327, 328, 329, 331, 332, 333,
            334, 336, 337, 338, 339, 340, 341, 342, 344, 346, 347, 348, 350, 351, 352, 354, 355, 356, 357, 358, 359, 360, 362, 367, 368, 370, 374, 375, 376, 377, 378,
            386, 387, 388, 389, 390, 395, 421, 422, 424, 444, 445, 446, 464, 466, 491, 492, 493, 494, 495, 496, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523,
            524, 525, 532, 533};

    //idle
    public static long timeFromMark(long t) {
        return currentTimeMillis() - t;
    }

    //int stuff

    public static String moneyPouch = "Coin Pouch";
    public static String jug = "Jug";
    public static int climbdownladder = 6260;
    public static int noteManager = 1615;
    public static int generalStore = 3537;
    public static int openCurtain = 1534;
    public static int closedCurtain = 1533;
    public static int staircasedownstairs = 6242;
    public static int staircaseupstairs = 6243;
    public static int WINE_ID = 1993;
    public static int NOTED_WINE_ID = 1994;
    public static int toEat = 0;
    public static int xp = Skills.experience(Constants.SKILLS_THIEVING);

    public static boolean ohshit() {
    return Npcs.stream().within(Configs.house).id(Configs.thug).filter(npc -> npc.overheadMessage().contains("I'll kill you for that")).isNotEmpty();
     }

    public static boolean inCombat() {
        return Npcs.stream().interactingWithMe().isNotEmpty() && Players.local().healthBarVisible();
    }

    public static boolean amfollowing() {
        return Npcs.stream().interactingWithMe().isNotEmpty();
    }

    public static boolean timetoJet() {
        return Players.local().healthBarVisible();
    }

    public static boolean snooze() {
        return Npcs.stream().within(Configs.house).id(Configs.thug).filter(npc -> npc.overheadMessage() == "Zzzzzz").isNotEmpty();

    }

    public static boolean wotudo() {
        return Npcs.stream().within(Configs.house).id(Configs.thug).filter(npc -> npc.overheadMessage() == "What do you think ").isNotEmpty();

    }

    public static boolean nearPlayer() {
        return Players.stream().filter(x->!x.equals(Players.local())).within(Configs.house).isNotEmpty();

    }

    //world switcher
    public static int loggedintoWorld() {
        String world_text = Widgets.widget(69).component(2).text();
        current_world = Integer.parseInt(world_text.substring(world_text.length() - 3));
        return current_world;
    }
    public static void hopWorld(World world) {
        world.hop();
        Condition.wait(() -> Players.local().animation() == -1, 250, 150);
    }
}
