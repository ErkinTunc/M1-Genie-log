package re.forestier.edu;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        player firstPlayer = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, new ArrayList<>());
        firstPlayer.addGameMoney(400);

        UpdatePlayer.addXp(firstPlayer, 15);
        System.out.println(Affichage.getPlayerSummary(firstPlayer));

        System.out.println("------------------");
        
        UpdatePlayer.addXp(firstPlayer, 20);
        System.out.println(Affichage.getPlayerSummary(firstPlayer));
    }

}