package re.forestier.edu.rpg;

/**
 * This class provides formatted player information as text.
 *
 * @author Erkin Tunc Boya
 */
public class Affichage {

    /**
     * @param player the chosen player
     * @return the player's information, abilities and inventory as a String
     */
    public static String getPlayerSummary(Player player) {

        String playerDescription = "";

        playerDescription += formatPlayerInfo(player);
        playerDescription += formatAbilities(player);
        playerDescription += formatInventory(player);

        return playerDescription;
    }

    // -------- Helper-Methods -------------

    private static String formatPlayerInfo(Player player) {
        StringBuilder result = new StringBuilder();

        result.append("Joueur ")
                .append(player.avatarName)
                .append(" joué par ")
                .append(player.playerName);

        result.append("\nNiveau : ")
                .append(player.getLevel())
                .append(" (XP totale : ")
                .append(player.xp)
                .append(")");

        return result.toString();
    }

    private static String formatAbilities(Player player) {
        StringBuilder result = new StringBuilder("\n\nCapacités :");

        player.abilities.forEach((name, level) -> {
            result.append("\n   ")
                    .append(name)
                    .append(" : ")
                    .append(level);
        });

        return result.toString();
    }

    private static String formatInventory(Player player) {
        StringBuilder result = new StringBuilder("\n\nInventaire :");

        for (Item item : player.inventory) {
            result.append("\n   ")
                    .append(item.getName())
                    .append(" : ")
                    .append(item.getDescription());
        }

        return result.toString();
    }
}
