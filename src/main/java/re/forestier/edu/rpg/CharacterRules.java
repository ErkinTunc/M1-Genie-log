package re.forestier.edu.rpg;

import java.util.HashMap;

/**
 * The class defines character abilities for each character class at each level
 * and determines the healing rules for each player based on their class.
 *
 * @author Erkin Tunc Boya
 */
public class CharacterRules {

    // ---------------- Ability definitions ----------------------

    /**
     * @return a map of character classes, where each class has its own levels
     *         and each level contains its abilities
     */
    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {

        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel = new HashMap<>();

        abilitiesPerTypeAndLevel.put("ADVENTURER", createAdventurerAbilities());
        abilitiesPerTypeAndLevel.put("ARCHER", createArcherAbilities());
        abilitiesPerTypeAndLevel.put("DWARF", createDwarfAbilities());

        return abilitiesPerTypeAndLevel;
    }

    private static HashMap<Integer, HashMap<String, Integer>> createAdventurerAbilities() {

        HashMap<Integer, HashMap<String, Integer>> abilitiesByLevel = new HashMap<>();

        HashMap<String, Integer> adventurerLevel1 = new HashMap<>();
        adventurerLevel1.put("INT", 1);
        adventurerLevel1.put("DEF", 1);
        adventurerLevel1.put("ATK", 3);
        adventurerLevel1.put("CHA", 2);
        abilitiesByLevel.put(1, adventurerLevel1);

        HashMap<String, Integer> adventurerLevel2 = new HashMap<>();
        adventurerLevel2.put("INT", 2);
        adventurerLevel2.put("CHA", 3);
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

    // ------------------ Ability lookup ------------------------

    static HashMap<String, Integer> getLevelAbilities(
            String avatarClass, int level) {

        return abilitiesPerTypeAndLevel()
                .get(avatarClass)
                .get(level);
    }

    static HashMap<String, Integer> getInitialAbilities(
            String avatarClass) {
        return getLevelAbilities(avatarClass, 1);
    }

    // ------------------ Character validation ------------------------

    static boolean isValidAvatarClass(String avatarClass) {
        return avatarClass.equals("ARCHER")
                || avatarClass.equals("ADVENTURER")
                || avatarClass.equals("DWARF");
    }

    // ------------------ Healing rules ------------------------

    /**
     * Each character has different regeneration rules, so we need
     * separate rules for each of them.
     *
     * @param player the player whose health points may be updated
     */
    public static void healByAvatarClass(Player player) {
        switch (player.getAvatarClass()) {
            case "ADVENTURER" -> healAdventurer(player);
            case "DWARF" -> healDwarf(player);
            case "ARCHER" -> healArcher(player);
            default -> {
            }
        }
    }

    private static void healAdventurer(Player player) {
        player.currentHealthPoints += 2;

        if (player.getLevel() < 3) {
            player.currentHealthPoints -= 1;
        }
    }

    private static void healDwarf(Player player) {
        player.currentHealthPoints += 1;

        if (player.inventory.contains("Holy Elixir : Recover your HP")) {
            player.currentHealthPoints += 1;
        }

    }

    private static void healArcher(Player player) {
        player.currentHealthPoints += 1;

        if (player.inventory.contains("Magic Bow")) {
            player.currentHealthPoints += player.currentHealthPoints / 8 - 1;
        }
    }

}
