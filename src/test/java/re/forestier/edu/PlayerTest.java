package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.Player;

import re.forestier.edu.rpg.Item;

// fail() fucntion
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

public class PlayerTest {

    private Player player1;

    @BeforeEach
    void setUp() {
        player1 = createPlayer("ADVENTURER");
    }

    private Player createPlayer(String avatarClass) {
        return new Player(
                "Florian",
                "Grognak le barbare",
                avatarClass,
                100,
                new ArrayList<>());
    }

    // ----------- Player Creation Tests -------------------------
    @Test
    void testPlayerName() {
        assertThat(player1.playerName, is("Florian"));
    }

    @Test
    void testAvatarName() {
        assertThat(player1.avatarName, is("Grognak le barbare"));
    }

    @Test
    void testAvatarClass() {
        assertThat(player1.getAvatarClass(), is("ADVENTURER"));
    }

    @Test
    void testPlayerGameMoney() {
        assertThat(player1.gameMoney, is(100));
    }

    @Test
    void testInventory() {
        assertThat(player1.inventory.isEmpty(), is(true));
    }

    @Test
    void testInvalidAvatarClass() {
        Player player = new Player(
                "Florian", "Hero", "MAGE",
                100, new ArrayList<>());

        assertThat(player.getAvatarClass(), is(nullValue()));
    }

    // ----------- Inventory Tests -----------

    @Test
    void testAddItemWithinCapacity() {
        Item ring = new Item("Lookout Ring");

        assertTrue(player1.addItem(ring));
        assertThat(player1.inventory.size(), is(1));
    }

    @Test
    void testRejectItemOverCapacity() {
        Player dwarf = createPlayer("DWARF");
        Item staff = new Item("Rune Staff of Curse");

        assertTrue(dwarf.addItem(staff));
        assertTrue(dwarf.addItem(staff));
        assertTrue(dwarf.addItem(staff)); // 6.6 kg

        assertFalse(dwarf.addItem(staff)); // 8.8 kg > 8.0 kg

        assertThat(dwarf.inventory.size(), is(3));
    }

    // ----------- Carry Capacity Tests -----------

    @Test
    void testDefaultCarryCapacity() {
        Player archer = createPlayer("ARCHER");

        assertThat(player1.getMaxCarryWeight(), is(10.0f));
        assertThat(archer.getMaxCarryWeight(), is(10.0f));
    }

    @Test
    void testClassSpecificCarryCapacity() {
        Player dwarf = createPlayer("DWARF");
        Player goblin = createPlayer("GOBLIN");

        assertThat(dwarf.getMaxCarryWeight(), is(8.0f));
        assertThat(goblin.getMaxCarryWeight(), is(12.0f));
    }

    // --------------- Player - addGameMoney() -------------------

    // Initial money = 100

    @Test
    void testAddGameMoneyPositive() {
        player1.addGameMoney(200);
        assertThat(player1.gameMoney, is(300));
    }

    @Test
    void testAddGameMoneyZero() {
        player1.addGameMoney(0);
        assertThat(player1.gameMoney, is(100));
    }

    @Test
    void testAddGameMoneyNegative() {
        player1.addGameMoney(-5);
        assertThat(player1.gameMoney, is(95));
    }

    // --------------- Player - removeGameMoney() -------------------

    // Initial money = 100

    @Test
    void testRemoveGameMoneyPositive() {
        player1.removeGameMoney(50);
        assertThat(player1.gameMoney, is(50));
    }

    @Test
    void testRemoveMoneyZero() {
        player1.removeGameMoney(0);
        assertThat(player1.gameMoney, is(100));
    }

    @Test
    void testRemoveGameMoneyNegative() {
        player1.removeGameMoney(-5);
        assertThat(player1.gameMoney, is(105));
    }

    @Test
    void testRemoveGameMoneyExactBalance() {
        player1.removeGameMoney(100);
        assertThat(player1.gameMoney, is(0));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testRemoveGameMoneyInsufficient() {
        assertThrows(
                IllegalArgumentException.class,
                () -> player1.removeGameMoney(101));

        assertThat(player1.gameMoney, is(100));
    }

    // -------------- Retrieve Level -----------------------

    // The attribute xp is protected in player.java soo we can not test it because
    // we no not have acces.

    // The thing we can do is we can test it through UpdatePlayer
    // because we have acces to that.

    // Level 1: XP < 10
    // ----------- Retrieve Level Tests -----------

    private void checkLevel(int xpToAdd, int expectedLevel) {
        UpdatePlayer.addXp(player1, xpToAdd);
        assertThat(player1.getLevel(), is(expectedLevel));
    }

    // Boundary: 10 XP
    @Test
    void testLevel2Boundary() {
        checkLevel(9, 1);
        checkLevel(1, 2);
        checkLevel(1, 2);
    }

    // Boundary: 27 XP
    @Test
    void testLevel3Boundary() {
        checkLevel(26, 2);
        checkLevel(1, 3);
        checkLevel(1, 3);
    }

    // Boundary: 57 XP
    @Test
    void testLevel4Boundary() {
        checkLevel(56, 3);
        checkLevel(1, 4);
        checkLevel(1, 4);
    }

    // Boundary: 111 XP
    @Test
    void testLevel5Boundary() {
        checkLevel(110, 4);
        checkLevel(1, 5);
        checkLevel(1, 5);
    }

    // -------------- getXP() ---------------

    @Test
    void testInitialXp() {
        assertThat(player1.getXp(), is(0));
    }

    @Test
    void testGetXpAfterAddingXp() {
        UpdatePlayer.addXp(player1, 5);

        assertThat(player1.getXp(), is(5));
    }
}
