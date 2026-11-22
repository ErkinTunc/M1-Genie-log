
package re.forestier.edu;

import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.Item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    // ----------- Item Creation Tests -----------

    @Test
    void testItemProperties() {
        Item item = new Item("Lookout Ring");

        assertThat(item.getName(), is("Lookout Ring"));
        assertThat(item.getDescription(), is("Prevents surprise attacks"));
        assertThat(item.getWeight(), is(0.1f));
        assertThat(item.getValue(), is(120));
    }

    @Test
    void testKnownItemsHaveValidProperties() {
        String[] itemNames = {
                "Lookout Ring",
                "Scroll of Stupidity",
                "Draupnir",
                "Magic Charm",
                "Rune Staff of Curse",
                "Combat Edge",
                "Holy Elixir",
                "Magic Bow"
        };

        for (String name : itemNames) {
            Item item = new Item(name);

            assertThat(item.getName(), is(name));
            assertFalse(item.getDescription().isBlank());
            assertTrue(item.getWeight() >= 0.1f);
            assertTrue(item.getValue() > 0);
        }
    }

    @Test
    void testUnknownItemRejected() {
        String[] invalidNames = { "Unknown Sword", "" };

        for (String name : invalidNames) {
            try {
                new Item(name);
                fail("Invalid item accepted: " + name);
            } catch (IllegalArgumentException expected) {
                // Expected: invalid item names are rejected.
            }
        }
    }

}
