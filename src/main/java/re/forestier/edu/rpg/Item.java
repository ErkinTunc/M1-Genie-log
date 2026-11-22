package re.forestier.edu.rpg;

public class Item {

    private final String name;
    private final String description;
    private final float weight;
    private final int value;

    // ------------- Constructor ------------

    public Item(String name) {
        ItemCatalog.Definition definition = ItemCatalog.get(name);

        this.name = name;
        this.description = definition.description();
        this.weight = definition.weight();
        this.value = definition.value();
    }

    // ----------- Getters -------------

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}
