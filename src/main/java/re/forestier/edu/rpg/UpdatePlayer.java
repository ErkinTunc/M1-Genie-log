package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;

public class UpdatePlayer {

    // ---------------- Constants -------------------

    private final static String[] AVAILABLE_ITEMS = {
            "Lookout Ring : Prevents surprise attacks",
            "Scroll of Stupidity : INT-2 when applied to an enemy",
            "Draupnir : Increases XP gained by 100%",
            "Magic Charm : Magic +10 for 5 rounds",
            "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?",
            "Combat Edge : Well, that's an edge",
            "Holy Elixir : Recover your HP"
    };

    // ---------------- Methods -------------------

    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        return CharacterRules.abilitiesPerTypeAndLevel();
    }

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
        HashMap<String, Integer> abilities = CharacterRules.getLevelAbilities(player.getAvatarClass(), newLevel);

        abilities.forEach((ability, level) -> {
            player.abilities.put(ability, level);
        });
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
            CharacterRules.healByAvatarClass(player);
        }
    }

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