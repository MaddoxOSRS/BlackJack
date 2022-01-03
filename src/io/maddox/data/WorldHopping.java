package io.maddox.data;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Components;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.World;
import org.powbot.api.rt4.Worlds;

public class WorldHopping {

        static String currentWorld = "0";

        public static void Hop(){

            if (IsWorldOpen()) {
                System.out.println("Worldhopper is open!");
                World randomWorld = GetRandomWorld();
                System.out.println("World to hop to: " + randomWorld.toString());
                TryChangeWorld(randomWorld);
                System.out.println("HoppP");
                IsWorldOpen();
            } else {
                System.out.println("Cant open worldhopper");
            }
        }

        private static World GetRandomWorld() {
            World randomWorld = Worlds.stream()
                    .filter(world ->
                            world.type() == World.Type.MEMBERS
                                    && world.specialty() != World.Specialty.HIGH_RISK
                                    && world.specialty() != World.Specialty.SKILL_REQUIREMENT
                                    && world.id() != 560 && world.id() != 86 && world.id() < 580
                                    && world.specialty() != World.Specialty.DEAD_MAN
                                    && world.specialty() != World.Specialty.PVP
                                    && world.id() != 319
                    ).shuffled().get(0);
            return randomWorld;
        }

        private static boolean TryChangeWorld(World world) {
            boolean success = world.hop();
            if (success) {
                return true;
            } else {
                world = GetRandomWorld();
                return TryChangeWorld(world);
            }
        }

        private static boolean IsWorldOpen() {
            if (Game.tab(Game.Tab.LOGOUT)) {
                if (Components.stream().text("Tap here to logout").viewable().findFirst().isPresent()) {
                    Components.stream().text("World Switcher").viewable().first().click();
                    Condition.wait(() -> Components.stream().text("Current world - ").viewable().findFirst().isPresent(), 150, 10);
                    String text = Components.stream().text("Current world - ").first().text();
                    currentWorld = text.substring(text.lastIndexOf(" ") + 1);
                    System.out.println(currentWorld);
                    return true;
                }
                if (Components.stream().text("Current world - ").viewable().findFirst().isPresent()) {
                    String text = Components.stream().text("Current world - ").first().text();
                    currentWorld = text.substring(text.lastIndexOf(" ") + 1);
                    System.out.println(currentWorld);
                    return true;
                }
            }
            return false;
        }
    }
