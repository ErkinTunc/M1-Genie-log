package re.forestier.edu.rpg;

public class Affichage {

    public static String getPlayerSummary(Player player) {

        final String[] playerDescription = { "Joueur " + player.avatarName + " joué par " + player.playerName };
        
        playerDescription[0] += "\nNiveau : " + player.getLevel() + " (XP totale : " + player.xp + ")";
        playerDescription[0] += "\n\nCapacités :";
        
        player.abilities.forEach((name, level) -> {
            playerDescription[0] += "\n   " + name + " : " + level;
        });
        
        playerDescription[0] += "\n\nInventaire :";
        
        player.inventory.forEach(item -> {
            playerDescription[0] += "\n   " + item;
        });

        return playerDescription[0];
    }
}
