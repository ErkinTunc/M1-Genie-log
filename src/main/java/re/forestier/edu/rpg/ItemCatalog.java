
package re.forestier.edu.rpg;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the available items and their properties.
 *
 * @author Erkin Tunc Boya
 */
final class ItemCatalog {

    // ------------ Constructor ------------

    private ItemCatalog() {
    }

    // ------------ Helper Method ------------------

    /**
     * Stores the predefined properties of an item.
     *
     * We use a record instead of a regular class because it keeps the data
     * immutable and reduces the amount of code. A regular class would need more
     * code for the constructor, getters and data comparison.
     *
     * @param description the item description
     * @param weight      the item weight in kilograms
     * @param value       the item value in gold
     */
    record Definition(String description, float weight, int value) {
    }

    // -------------- Constant Map -----------------

    private static final Map<String, Definition> ITEMS = Map.of(
            "Lookout Ring",
            new Definition("Prevents surprise attacks", 0.1f, 120),

            "Scroll of Stupidity",
            new Definition("INT-2 when applied to an enemy", 0.1f, 30),

            "Draupnir",
            new Definition("Increases XP gained by 100%", 0.2f, 250),

            "Magic Charm",
            new Definition("Magic +10 for 5 rounds", 0.2f, 110),

            "Rune Staff of Curse",
            new Definition("May burn your ennemies... Or yourself. Who knows?", 2.2f, 180),

            "Combat Edge",
            new Definition("Well, that's an edge", 1.4f, 130),

            "Holy Elixir",
            new Definition("Recover your HP", 0.4f, 60),

            "Magic Bow",
            new Definition("A magical bow", 1.0f, 200));

    // ------------ Getter --------------

    static Definition get(String name) {
        if (name == null || !ITEMS.containsKey(name)) {
            throw new IllegalArgumentException("Unknown item: " + name);
        }

        return ITEMS.get(name);
    }

    static List<String> getLootableItemNames() {
        List<String> names = new ArrayList<>(ITEMS.keySet());

        names.remove("Magic Bow");

        return names;
    }

}
