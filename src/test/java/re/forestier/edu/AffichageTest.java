package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AffichageTest {

    private player player1;

    @BeforeEach
    void setUp() {
        player1 = new player(
                "Florian",
                "Grognak le barbare",
                "ADVENTURER",
                100,
                new ArrayList<>());
    }

    // ----------- Player Information -----------

    @Test
    void testPlayerInformation() {
        String result = Affichage.getPlayerSummary(player1);

        assertThat(result, containsString(
                "Joueur Grognak le barbare joué par Florian"));
    }

    // ----------- Level and XP -----------

    @Test
    void testLevelAndXp() {
        UpdatePlayer.addXp(player1, 5);

        String result = Affichage.getPlayerSummary(player1);

        assertThat(result, containsString(
                "Niveau : 1 (XP totale : 5)"));
    }

    // ----------- Abilities -----------

    @Test
    void testAbilities() {
        String result = Affichage.getPlayerSummary(player1);

        assertThat(result, containsString("Capacités :"));
        assertThat(result, containsString("INT : 2"));
        assertThat(result, containsString("DEF : 1"));
        assertThat(result, containsString("ATK : 3"));
        assertThat(result, containsString("CHA : 3"));
    }

    // ----------- Empty Inventory -----------

    @Test
    void testEmptyInventory() {
        String result = Affichage.getPlayerSummary(player1);

        assertThat(result, endsWith("Inventaire :"));
    }

    // ----------- Inventory With Items -----------

    @Test
    void testInventoryWithItems() {
        player1.inventory.add("Magic Bow");
        player1.inventory.add("Holy Elixir");

        String result = Affichage.getPlayerSummary(player1);

        assertThat(result, containsString("Magic Bow"));
        assertThat(result, containsString("Holy Elixir"));
    }
}