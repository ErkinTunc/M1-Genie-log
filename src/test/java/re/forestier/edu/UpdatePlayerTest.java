package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdatePlayerTest {

    private player player1; // ADVENTURER

    @BeforeEach
    void setUp() {
        player1 = new player(
                "Florian",
                "Grognak le barbare",
                "ADVENTURER",
                100,
                new ArrayList<>());
    }

    private player createPlayer(String type) {
        player player = new player(
                "Florian", "Grognak le barbare",
                type, 100, new ArrayList<>());

        player.healthpoints = 100;
        player.currenthealthpoints = 40;

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
        assertThat(player1.retrieveLevel(), is(1));
        assertThat(result, is(false));
    }

    @Test
    void testAddXpWithLevelUp() {
        boolean result = UpdatePlayer.addXp(player1, 10);

        assertThat(player1.getXp(), is(10));
        assertThat(player1.retrieveLevel(), is(2));
        assertThat(result, is(true));
        assertThat(player1.inventory.size(), is(1)); // Add a random item
    }

    @Test
    void testAddXpUpdatesAbilities() {
        UpdatePlayer.addXp(player1, 27);

        assertThat(player1.retrieveLevel(), is(3));
        assertThat(player1.abilities.get("ATK"), is(5));
        assertThat(player1.abilities.get("ALC"), is(1));
    }

    @Test
    void testAddXpMultipleLevels() {
        boolean result = UpdatePlayer.addXp(player1, 57);

        assertThat(result, is(true));
        assertThat(player1.retrieveLevel(), is(4));
        assertThat(player1.inventory.size(), is(1));
        assertThat(player1.abilities.get("DEF"), is(3));
    }

    // ---------------------- majDinDeTour method Testing ----------------

    @Test
    void testPlayerKO() {
        player1.healthpoints = 100;
        player1.currenthealthpoints = 0;

        UpdatePlayer.majFinDeTour(player1);

        assertThat(player1.currenthealthpoints, is(0));
    }

    @Test
    void testHealthBelowHalf() {
        player1.healthpoints = 100;
        player1.currenthealthpoints = 40;

        UpdatePlayer.majFinDeTour(player1);

        assertThat(player1.currenthealthpoints, is(41)); // player1 starts with Level1
        // 40 + 2 - 1 = 41
    }

    @Test
    void testHealthExactlyHalf() {
        player1.healthpoints = 100;
        player1.currenthealthpoints = 50;

        UpdatePlayer.majFinDeTour(player1);

        assertThat(player1.currenthealthpoints, is(50));
    }

    @Test
    void testHealthAboveMaximum() {
        player1.healthpoints = 100;
        player1.currenthealthpoints = 120;

        UpdatePlayer.majFinDeTour(player1);

        assertThat(player1.currenthealthpoints, is(100));
    }

    // --- Adventurer ---
    @Test
    void testAdventurerLevel3Healing() {
        player1.healthpoints = 100;
        player1.currenthealthpoints = 40;

        UpdatePlayer.addXp(player1, 27);
        UpdatePlayer.majFinDeTour(player1);

        assertThat(player1.currenthealthpoints, is(42));
    }

    // --- Dwarf ---
    @Test
    void testDwarfHealing() {
        player player = createPlayer("DWARF");

        UpdatePlayer.majFinDeTour(player);

        assertThat(player.currenthealthpoints, is(41));
    }

    @Test
    void testDwarfWithHolyElixir() {
        player player = createPlayer("DWARF");
        player.inventory.add("Holy Elixir");

        UpdatePlayer.majFinDeTour(player);

        assertThat(player.currenthealthpoints, is(42));
    }

    // --- Archer ---
    @Test
    void testArcherHealing() {
        player player = createPlayer("ARCHER");

        UpdatePlayer.majFinDeTour(player);

        assertThat(player.currenthealthpoints, is(41));
    }

    @Test
    void testArcherWithMagicBow() {
        player player = createPlayer("ARCHER");
        player.inventory.add("Magic Bow");

        UpdatePlayer.majFinDeTour(player);

        assertThat(player.currenthealthpoints, is(45));
    }
}