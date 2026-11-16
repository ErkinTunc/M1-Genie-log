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

        abilitiesPerTypeAndLevel.put("ADVENTURER", createAdventurerAbilities());
        abilitiesPerTypeAndLevel.put("ARCHER", createArcherAbilities());
        abilitiesPerTypeAndLevel.put("DWARF", createDwarfAbilities());

        return abilitiesPerTypeAndLevel;
    }

    // --------- Sub-Methods --------------

    private static HashMap<Integer, HashMap<String, Integer>> createAdventurerAbilities() {

        HashMap<Integer, HashMap<String, Integer>> abilitiesByLevel = new HashMap<>();

        HashMap<String, Integer> adventurerLevel1 = new HashMap<>();
        adventurerLevel1.put("INT", 1);
        adventurerLevel1.put("DEF", 1);
        adventurerLevel1.put("ATK", 3);
        adventurerLevel1.put("CHA", 2);
        abilitiesByLevel.put(1, adventurerLevel1);

        HashMap<String, Integer> adventurerLevel2 = new HashMap<>();
        adventurerLevel1.put("INT", 2); // FIX
        adventurerLevel1.put("CHA", 3); // FIX
        abilitiesByLevel.put(2, adventurerLevel2);

        HashMap<String, Integer> adventurerLevel3 = new HashMap<>();
        adventurerLevel3.put("ATK", 5);
        adventurerLevel3.put("ALC", 1);
        abilitiesByLevel.put(3, adventurerLevel3);

        HashMap<String, Integer> adventurerLevel4 = new HashMap<>();
        adventurerLevel4.put("DEF", 3);
        abilitiesByLevel.put(4, adventurerLevel4);

        HashMap<String, Integer> adventurerLevel5 = new HashMap<>();
        adventurerLevel5.put("VIS", 1);
        adventurerLevel5.put("DEF", 4);
        abilitiesByLevel.put(5, adventurerLevel5);

        return abilitiesByLevel;
    }

    private static HashMap<Integer, HashMap<String, Integer>> createArcherAbilities() {

        HashMap<Integer, HashMap<String, Integer>> abilitiesByLevel = new HashMap<>();

        HashMap<String, Integer> archerLevel1 = new HashMap<>();
        archerLevel1.put("INT", 1);
        archerLevel1.put("ATK", 3);
        archerLevel1.put("CHA", 1);
        archerLevel1.put("VIS", 3);
        abilitiesByLevel.put(1, archerLevel1);

        HashMap<String, Integer> archerLevel2 = new HashMap<>();
        archerLevel2.put("DEF", 1);
        archerLevel2.put("CHA", 2);
        abilitiesByLevel.put(2, archerLevel2);

        HashMap<String, Integer> archerLevel3 = new HashMap<>();
        archerLevel3.put("ATK", 3);
        abilitiesByLevel.put(3, archerLevel3);

        HashMap<String, Integer> archerLevel4 = new HashMap<>();
        archerLevel4.put("DEF", 2);
        abilitiesByLevel.put(4, archerLevel4);

        HashMap<String, Integer> archerLevel5 = new HashMap<>();
        archerLevel5.put("ATK", 4);
        abilitiesByLevel.put(5, archerLevel5);

        return abilitiesByLevel;
    }

    private static HashMap<Integer, HashMap<String, Integer>> createDwarfAbilities() {

        HashMap<Integer, HashMap<String, Integer>> abilitiesByLevel = new HashMap<>();

        HashMap<String, Integer> dwarfLevel1 = new HashMap<>();
        dwarfLevel1.put("ALC", 4);
        dwarfLevel1.put("INT", 1);
        dwarfLevel1.put("ATK", 3);
        abilitiesByLevel.put(1, dwarfLevel1);

        HashMap<String, Integer> dwarfLevel2 = new HashMap<>();
        dwarfLevel2.put("DEF", 1);
        dwarfLevel2.put("ALC", 5);
        abilitiesByLevel.put(2, dwarfLevel2);

        HashMap<String, Integer> dwarfLevel3 = new HashMap<>();
        dwarfLevel3.put("ATK", 4);
        abilitiesByLevel.put(3, dwarfLevel3);

        HashMap<String, Integer> dwarfLevel4 = new HashMap<>();
        dwarfLevel4.put("DEF", 2);
        abilitiesByLevel.put(4, dwarfLevel4);

        HashMap<String, Integer> dwarfLevel5 = new HashMap<>();
        dwarfLevel5.put("CHA", 1);
        abilitiesByLevel.put(5, dwarfLevel5);

        return abilitiesByLevel;
    }

    // -----------------------------------

    public static boolean addXp(Player player, int xp) {

        int currentLevel = player.getLevel();
        player.xp += xp;
        int newLevel = player.getLevel();

        if (newLevel != currentLevel) {
            // Player leveled-up!
            giveRandomItem(player);
            updateAbilities(player, newLevel);
            return true;
        }
        return false;
    }

    // --------- Sub-Methods --------------

    private static void giveRandomItem(Player player) {
        Random random = new Random();
        player.inventory.add(AVAILABLE_ITEMS[random.nextInt(AVAILABLE_ITEMS.length)]);
    }

    private static void updateAbilities(Player player, int newLevel) {
        HashMap<String, Integer> abilities = getLevelAbilities(player.getAvatarClass(), newLevel);

        abilities.forEach((ability, level) -> {
            player.abilities.put(ability, level);
        });
    }

    private static HashMap<String, Integer> getLevelAbilities(
            String avatarClass, int level) {

        return abilitiesPerTypeAndLevel()
                .get(avatarClass)
                .get(level);
    }

    // -----------------------------------

    public static void updateHealthAtEndOfTurn(Player player) {

        if (isPlayerKO(player)) {
            return;
        }

        applyHealthRegeneration(player);
        capHealthToMaximum(player);
    }

    // -------- Sub-Methods -------------

    private static boolean isPlayerKO(Player player) {
        if (player.currentHealthPoints == 0) {
            System.out.println("Le joueur est KO !");
            return true;
        }
        return false;
    }

    private static void applyHealthRegeneration(Player player) {
        if (player.currentHealthPoints < player.maxHealthPoints / 2) {
            healByAvatarClass(player);
        }
    }

    private static void healByAvatarClass(Player player) {
        if (player.getAvatarClass().equals("ADVENTURER")) {
            healAdventurer(player);

        } else if (player.getAvatarClass().equals("DWARF")) {
            healDwarf(player);

        } else if (player.getAvatarClass().equals("ARCHER")) {
            healArcher(player);
        }
    }

    // -------- Sub-Sub-Methods -------------

    private static void healAdventurer(Player player) {
        player.currentHealthPoints += 2;

        if (player.getLevel() < 3) {
            player.currentHealthPoints -= 1;
        }
    }

    private static void healDwarf(Player player) {
        player.currentHealthPoints += 1;

        if (player.inventory.contains("Holy Elixir")) {
            player.currentHealthPoints += 1;
        }

    }

    private static void healArcher(Player player) {
        player.currentHealthPoints += 1;

        if (player.inventory.contains("Magic Bow")) {
            player.currentHealthPoints += player.currentHealthPoints / 8 - 1;
        }
    }
    // -----------------------------------

    private static void capHealthToMaximum(Player player) {
        if (player.currentHealthPoints > player.maxHealthPoints) {
            player.currentHealthPoints = player.maxHealthPoints;
        }
    }

    // ------------ Getters ----------------

    public static HashMap<String, Integer> getInitialAbilities(String avatarClass) {
        return abilitiesPerTypeAndLevel().get(avatarClass).get(1);
    }
}