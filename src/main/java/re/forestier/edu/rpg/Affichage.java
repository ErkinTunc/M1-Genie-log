package re.forestier.edu.rpg;

public class Affichage {

    public static String getPlayerSummary(Player player) {

        String playerDescription = "";

        playerDescription += formatPlayerInfo(player);
        playerDescription += formatAbilities(player);
        playerDescription += formatInventory(player);

        return playerDescription;
    }

    // -------- Sub-Methods -------------

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

        player.inventory.forEach(item -> {
            result.append("\n   ").append(item);
        });

        return result.toString();
    }
}
