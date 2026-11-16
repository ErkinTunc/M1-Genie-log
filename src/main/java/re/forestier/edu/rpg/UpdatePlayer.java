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

    // ---------------- Public API -------------------

    // Kept for compatibility with existing callers and tests.
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

    public static void updateHealthAtEndOfTurn(Player player) {

        if (player.currentHealthPoints == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }

        applyHealthRegeneration(player);
        capHealthToMaximum(player);
    }

    // ---------------- XP helpers ---------------------

    private static void giveRandomItem(Player player) {
        Random random = new Random();
        player.inventory.add(AVAILABLE_ITEMS[random.nextInt(AVAILABLE_ITEMS.length)]);
    }

    private static void updateAbilities(Player player, int newLevel) {
        HashMap<String, Integer> abilities = CharacterRules.getLevelAbilities(
                player.getAvatarClass(), newLevel);

        player.abilities.putAll(abilities);
    }

    // --------------- Health helpers -------------------

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

}