package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail; // fail() fucntion

import java.util.ArrayList;

public class PlayerTest {

    private player player1;

    @BeforeEach
    void setUp() {
        this.player1 = new player(
                "Florian",
                "Grognak le barbare",
                "ADVENTURER",
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
        assertThat(player1.Avatar_name, is("Grognak le barbare"));
    }

    @Test
    void testAvatarClass() {
        assertThat(player1.getAvatarClass(), is("ADVENTURER"));
    }

    @Test
    void testPlayerMoney() {
        assertThat(player1.money, is(100));
    }

    @Test
    void testInventory() {
        assertThat(player1.inventory.isEmpty(), is(true));
    }

    // --------------- Player - addMoeny() -------------------

    // Initial money = 100

    @Test
    void testAddMoneyPositive() {
        player1.addMoney(200);
        assertThat(player1.money, is(300));
    }

    @Test
    void testAddMoneyZero() {
        player1.addMoney(0);
        assertThat(player1.money, is(100));
    }

    @Test
    void testAddMoneyNegative() {
        player1.addMoney(-5);
        assertThat(player1.money, is(95));
    }

    // --------------- Player - removeMoney() -------------------

    // Initial money = 100

    @Test
    void testRemoveMoneyPositive() {
        player1.removeMoney(50);
        assertThat(player1.money, is(50));
    }

    @Test
    void testRemoveMoneyZero() {
        player1.removeMoney(0);
        assertThat(player1.money, is(100));
    }

    @Test
    void testRemoveMoneyNegative() {
        player1.removeMoney(-5);
        assertThat(player1.money, is(105));
    }

    @Test
    void testRemoveMoneyExactBalance() {
        player1.removeMoney(100);
        assertThat(player1.money, is(0));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testRemoveMoneyInsufficient() {
        assertThrows(
                IllegalArgumentException.class,
                () -> player1.removeMoney(101));

        assertThat(player1.money, is(100));
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
        assertThat(player1.retrieveLevel(), is(expectedLevel));
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
