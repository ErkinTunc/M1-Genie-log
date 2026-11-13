package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;

public class UpdatePlayer {

    private final static String[] AVAILABLE_ITEMS = {
            "Lookout Ring : Prevents surprise attacks",
            "Scroll of Stupidity : INT-2 when applied to an enemy",
            "Draupnir : Increases XP gained by 100%",
            "Magic Charm : Magic +10 for 5 rounds",
            "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?",
            "Combat Edge : Well, that's an edge",
            "Holy Elixir : Recover your HP"
    };

    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {

        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel = new HashMap<>();

        HashMap<Integer, HashMap<String, Integer>> adventurerAbilitiesByLevel = new HashMap<>();
        HashMap<String, Integer> adventurerLevel1 = new HashMap<>();
        adventurerLevel1.put("INT", 1);
        adventurerLevel1.put("DEF", 1);
        adventurerLevel1.put("ATK", 3);
        adventurerLevel1.put("CHA", 2);
        adventurerAbilitiesByLevel.put(1, adventurerLevel1);

        HashMap<String, Integer> adventurerLevel2 = new HashMap<>();
        adventurerLevel1.put("INT", 2);
        adventurerLevel1.put("CHA", 3);
        adventurerAbilitiesByLevel.put(2, adventurerLevel2);

        HashMap<String, Integer> adventurerLevel3 = new HashMap<>();
        adventurerLevel3.put("ATK", 5);
        adventurerLevel3.put("ALC", 1);
        adventurerAbilitiesByLevel.put(3, adventurerLevel3);

        HashMap<String, Integer> adventurerLevel4 = new HashMap<>();
        adventurerLevel4.put("DEF", 3);
        adventurerAbilitiesByLevel.put(4, adventurerLevel4);

        HashMap<String, Integer> adventurerLevel5 = new HashMap<>();
        adventurerLevel5.put("VIS", 1);
        adventurerLevel5.put("DEF", 4);
        adventurerAbilitiesByLevel.put(5, adventurerLevel5);

        abilitiesPerTypeAndLevel.put("ADVENTURER", adventurerAbilitiesByLevel);

        HashMap<Integer, HashMap<String, Integer>> archerAbilitiesByLevel = new HashMap<>();
        HashMap<String, Integer> archerLevel1 = new HashMap<>();
        archerLevel1.put("INT", 1);
        archerLevel1.put("ATK", 3);
        archerLevel1.put("CHA", 1);
        archerLevel1.put("VIS", 3);
        archerAbilitiesByLevel.put(1, archerLevel1);

        HashMap<String, Integer> archerLevel2 = new HashMap<>();
        archerLevel2.put("DEF", 1);
        archerLevel2.put("CHA", 2);
        archerAbilitiesByLevel.put(2, archerLevel2);

        HashMap<String, Integer> archerLevel3 = new HashMap<>();
        archerLevel3.put("ATK", 3);
        archerAbilitiesByLevel.put(3, archerLevel3);

        HashMap<String, Integer> archerLevel4 = new HashMap<>();
        archerLevel4.put("DEF", 2);
        archerAbilitiesByLevel.put(4, archerLevel4);

        HashMap<String, Integer> archerLevel5 = new HashMap<>();
        archerLevel5.put("ATK", 4);
        archerAbilitiesByLevel.put(5, archerLevel5);

        abilitiesPerTypeAndLevel.put("ARCHER", archerAbilitiesByLevel);

        HashMap<Integer, HashMap<String, Integer>> dwarfAbilitiesByLevel = new HashMap<>();
        HashMap<String, Integer> dwarfLevel1 = new HashMap<>();
        dwarfLevel1.put("ALC", 4);
        dwarfLevel1.put("INT", 1);
        dwarfLevel1.put("ATK", 3);
        dwarfAbilitiesByLevel.put(1, dwarfLevel1);

        HashMap<String, Integer> dwarfLevel2 = new HashMap<>();
        dwarfLevel2.put("DEF", 1);
        dwarfLevel2.put("ALC", 5);
        dwarfAbilitiesByLevel.put(2, dwarfLevel2);

        HashMap<String, Integer> dwarfLevel3 = new HashMap<>();
        dwarfLevel3.put("ATK", 4);
        dwarfAbilitiesByLevel.put(3, dwarfLevel3);

        HashMap<String, Integer> dwarfLevel4 = new HashMap<>();
        dwarfLevel4.put("DEF", 2);
        dwarfAbilitiesByLevel.put(4, dwarfLevel4);

        HashMap<String, Integer> dwarfLevel5 = new HashMap<>();
        dwarfLevel5.put("CHA", 1);
        dwarfAbilitiesByLevel.put(5, dwarfLevel5);

        abilitiesPerTypeAndLevel.put("DWARF", dwarfAbilitiesByLevel);

        return abilitiesPerTypeAndLevel;
    }

    public static boolean addXp(Player player, int xp) {
        int currentLevel = player.getLevel();
        player.xp += xp;
        int newLevel = player.getLevel();

        if (newLevel != currentLevel) {
            // Player leveled-up!
            // Give a random object
            ;
            Random random = new Random();
            player.inventory.add(AVAILABLE_ITEMS[random.nextInt(AVAILABLE_ITEMS.length - 0) + 0]);

            // Add/upgrade abilities to player
            HashMap<String, Integer> abilities = abilitiesPerTypeAndLevel().get(player.getAvatarClass()).get(newLevel);
            abilities.forEach((ability, level) -> {
                player.abilities.put(ability, abilities.get(ability));
            });
            return true;
        }
        return false;
    }

    // updateHealthAtEndOfTurn met à jour les points de vie
    public static void updateHealthAtEndOfTurn(Player player) {
        if (player.currentHealthPoints == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }

        if (player.currentHealthPoints < player.maxHealthPoints / 2) {
            if (!player.getAvatarClass().equals("ADVENTURER")) {
                if (player.getAvatarClass().equals("DWARF")) {
                    if (player.inventory.contains("Holy Elixir")) {
                        player.currentHealthPoints += 1;
                    }
                    player.currentHealthPoints += 1;
                } else if (player.getAvatarClass().equals("ADVENTURER")) {
                    player.currentHealthPoints += 2;
                }

                if (player.getAvatarClass().equals("ARCHER")) {
                    player.currentHealthPoints += 1;
                    if (player.inventory.contains("Magic Bow")) {
                        player.currentHealthPoints += player.currentHealthPoints / 8 - 1;
                    }
                }
            } else {
                player.currentHealthPoints += 2;
                if (player.getLevel() < 3) {
                    player.currentHealthPoints -= 1;
                }
            }
        } else if (player.currentHealthPoints >= player.maxHealthPoints / 2) {
            if (player.currentHealthPoints >= player.maxHealthPoints) {
                player.currentHealthPoints = player.maxHealthPoints;
                return;
            }
        }

        if (player.currentHealthPoints >= player.maxHealthPoints) {
            player.currentHealthPoints = player.maxHealthPoints;
        }
    }
}