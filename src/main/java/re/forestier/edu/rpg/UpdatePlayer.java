package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;

/**
 * This class manages updates to the player's attributes,
 * level progression, health regeneration, and abilities.
 *
 * @author Erkin Tunc Boya
 */
public class UpdatePlayer {

    // ---------------- Public API -------------------

    /**
     * Kept for compatibility with existing callers and tests.
     *
     * @return a map of character abilities for each class and level
     */
    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        return CharacterRules.abilitiesPerTypeAndLevel();
    }

    /**
     * If the player levels up with the given XP, they get a new random item and
     * receive updated abilities.
     *
     * @param player the player whose XP will change
     * @param xp     the XP that will be added
     * @return true if the player levels up, false otherwise
     */
    public static boolean addXp(Player player, int xp) {

        int currentLevel = player.getLevel();
        player.xp += xp;
        int newLevel = player.getLevel();

        if (newLevel > currentLevel) {
            giveRandomItem(player);

            for (int levelToApply = currentLevel + 1; levelToApply <= newLevel; levelToApply++) {

                updateAbilities(player, levelToApply);
            }

            return true;
        }
        return false;
    }

    /**
     * Health regeneration happens when the player has less than half
     * of their maximum health.
     * If HP is 0, a KO message is printed and no regeneration occurs.
     * If the player's HP is above the maximum limit, it is reduced
     * to the maximum possible HP.
     *
     * @param player the player whose health will be updated
     */
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

        var itemNames = ItemCatalog.getLootableItemNames();

        int randomIndex = random.nextInt(itemNames.size());
        String selectedItemName = itemNames.get(randomIndex);

        Item item = new Item(selectedItemName);
        player.addItem(item);
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