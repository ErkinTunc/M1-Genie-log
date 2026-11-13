package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdatePlayerTest {

    private player player1; // ADVENTURER

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpPlayer() {
        player1 = new player(
                "Florian",
                "Grognak le barbare",
                "ADVENTURER",
                100,
                new ArrayList<>());
    }

    @BeforeEach
    public void setUpOutputStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }

    private player createPlayer(String type) {
        player player = new player(
                "Florian", "Grognak le barbare",
                type, 100, new ArrayList<>());

        player.maxHealthPoints = 100;
        player.currentHealthPoints = 40;

        return player;
    }

    // ----------- Abilities Tests -----------

    @Test
    void testAdventurerAbilities() {
        var abilities = UpdatePlayer.abilitiesPerTypeAndLevel()
                .get("ADVENTURER");

        assertThat(abilities.get(1),
                is(Map.of("INT", 2, "DEF", 1,
                        "ATK", 3, "CHA", 3)));

        // Known bug: Level 2 abilities are empty
        assertThat(abilities.get(2), is(Map.of()));

        assertThat(abilities.get(3),
                is(Map.of("ATK", 5, "ALC", 1)));

        assertThat(abilities.get(4),
                is(Map.of("DEF", 3)));

        assertThat(abilities.get(5),
                is(Map.of("VIS", 1, "DEF", 4)));
    }

    @Test
    void testArcherAbilities() {
        var abilities = UpdatePlayer.abilitiesPerTypeAndLevel()
                .get("ARCHER");

        assertThat(abilities.get(1),
                is(Map.of("INT", 1, "ATK", 3,
                        "CHA", 1, "VIS", 3)));

        assertThat(abilities.get(2),
                is(Map.of("DEF", 1, "CHA", 2)));

        assertThat(abilities.get(3),
                is(Map.of("ATK", 3)));

        assertThat(abilities.get(4),
                is(Map.of("DEF", 2)));

        assertThat(abilities.get(5),
                is(Map.of("ATK", 4)));
    }

    @Test
    void testDwarfAbilities() {
        var abilities = UpdatePlayer.abilitiesPerTypeAndLevel()
                .get("DWARF");

        assertThat(abilities.get(1),
                is(Map.of("ALC", 4, "INT", 1, "ATK", 3)));

        assertThat(abilities.get(2),
                is(Map.of("DEF", 1, "ALC", 5)));

        assertThat(abilities.get(3),
                is(Map.of("ATK", 4)));

        assertThat(abilities.get(4),
                is(Map.of("DEF", 2)));

        assertThat(abilities.get(5),
                is(Map.of("CHA", 1)));
    }

    // ----------- addXp Method Tests -------------------------

    @Test
    void testAddXpWithoutLevelUp() {
        boolean result = UpdatePlayer.addXp(player1, 5);

        assertThat(player1.getXp(), is(5));
        assertThat(player1.getLevel(), is(1));
        assertThat(result, is(false));
    }

    @Test
    void testAddXpWithLevelUp() {
        boolean result = UpdatePlayer.addXp(player1, 10);

        assertThat(player1.getXp(), is(10));
        assertThat(player1.getLevel(), is(2));
        assertThat(result, is(true));
        assertThat(player1.inventory.size(), is(1)); // Add a random item
    }

    @Test
    void testAddXpUpdatesAbilities() {
        UpdatePlayer.addXp(player1, 27);

        assertThat(player1.getLevel(), is(3));
        assertThat(player1.abilities.get("ATK"), is(5));
        assertThat(player1.abilities.get("ALC"), is(1));
    }

    @Test
    void testAddXpMultipleLevels() {
        boolean result = UpdatePlayer.addXp(player1, 57);

        assertThat(result, is(true));
        assertThat(player1.getLevel(), is(4));
        assertThat(player1.inventory.size(), is(1));
        assertThat(player1.abilities.get("DEF"), is(3));
    }

    // ---------------------- majDinDeTour method Testing ----------------

    @Test
    void testPlayerKO() {
        player1.maxHealthPoints = 100;
        player1.currentHealthPoints = 0;

        UpdatePlayer.updateHealthAtEndOfTurn(player1);
        assertThat(player1.currentHealthPoints, is(0));

        assertThat(
                outputStream.toString(),
                containsString("Le joueur est KO !"));
    }

    @Test
    void testHealthBelowHalf() {
        player1.maxHealthPoints = 100;
        player1.currentHealthPoints = 40;

        UpdatePlayer.updateHealthAtEndOfTurn(player1);

        assertThat(player1.currentHealthPoints, is(41)); // player1 starts with Level1
        // 40 + 2 - 1 = 41
    }

    @Test
    void testHealthExactlyHalf() {
        player1.maxHealthPoints = 100;
        player1.currentHealthPoints = 50;

        UpdatePlayer.updateHealthAtEndOfTurn(player1);

        assertThat(player1.currentHealthPoints, is(50));
    }

    @Test
    void testHealthAboveMaximum() {
        player1.maxHealthPoints = 100;
        player1.currentHealthPoints = 120;

        UpdatePlayer.updateHealthAtEndOfTurn(player1);

        assertThat(player1.currentHealthPoints, is(100));
    }

    // --- Adventurer ---
    @Test
    void testAdventurerLevel3Healing() {
        player1.maxHealthPoints = 100;
        player1.currentHealthPoints = 40;

        UpdatePlayer.addXp(player1, 27);
        UpdatePlayer.updateHealthAtEndOfTurn(player1);

        assertThat(player1.currentHealthPoints, is(42));
    }

    // --- Dwarf ---
    @Test
    void testDwarfHealing() {
        player player = createPlayer("DWARF");

        UpdatePlayer.updateHealthAtEndOfTurn(player);

        assertThat(player.currentHealthPoints, is(41));
    }

    @Test
    void testDwarfWithHolyElixir() {
        player player = createPlayer("DWARF");
        player.inventory.add("Holy Elixir");

        UpdatePlayer.updateHealthAtEndOfTurn(player);

        assertThat(player.currentHealthPoints, is(42));
    }

    // --- Archer ---
    @Test
    void testArcherHealing() {
        player player = createPlayer("ARCHER");

        UpdatePlayer.updateHealthAtEndOfTurn(player);

        assertThat(player.currentHealthPoints, is(41));
    }

    @Test
    void testArcherWithMagicBow() {
        player player = createPlayer("ARCHER");
        player.inventory.add("Magic Bow");

        UpdatePlayer.updateHealthAtEndOfTurn(player);

        assertThat(player.currentHealthPoints, is(45));
    }

    // ----------- Invalid Health State -----------

    @Test
    void testInvalidHealthState() {
        UpdatePlayer.addXp(player1, 27);

        player1.maxHealthPoints = 0;
        player1.currentHealthPoints = -1;

        UpdatePlayer.updateHealthAtEndOfTurn(player1);

        assertThat(player1.currentHealthPoints, is(0));
    }
}