package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    public String playerName;
    public String avatarName;
    private String avatarClass;

    public Integer gameMoney;
    private Float realMoney;

    public int level;
    public int maxHealthPoints;
    public int currentHealthPoints;
    protected int xp;

    public HashMap<String, Integer> abilities;
    public ArrayList<String> inventory;

    // ----------- Constructor ----------------

    public Player(String playerName, String avatarName, String avatarClass, int money, ArrayList<String> inventory) {
        if (!avatarClass.equals("ARCHER") &&
                !avatarClass.equals("ADVENTURER") &&
                !avatarClass.equals("DWARF")) {
            return;
        }

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = avatarClass;
        this.gameMoney = Integer.valueOf(money);
        this.inventory = inventory;
        this.abilities = UpdatePlayer.abilitiesPerTypeAndLevel().get(avatarClass).get(1);
    }

    // ----------- Methods ----------------

    public void removeGameMoney(int amount) throws IllegalArgumentException {
        if (gameMoney - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }

        gameMoney = Integer.parseInt(gameMoney.toString()) - amount;
    }

    public void addGameMoney(int amount) {
        gameMoney = gameMoney + amount;
    }

    // ----------- Getters ----------------

    public String getAvatarClass() {
        return avatarClass;
    }

    public int getLevel() {

        HashMap<Integer, Integer> levels = createLevelThresholds();

        if (xp < levels.get(2)) {
            return 1;
        } else if (xp < levels.get(3)) {
            return 2;
        }
        if (xp < levels.get(4)) {
            return 3;
        }
        if (xp < levels.get(5))
            return 4;
        return 5;
    }

    // -------- Sub-Methods ------------

    private static HashMap<Integer, Integer> createLevelThresholds() {
        HashMap<Integer, Integer> levels = new HashMap<>();

        // XP threshold formula (integer division):
        // XP(n) = (n - 1) * 10 + (n * XP(n - 1)) / 4

        levels.put(2, 10);
        levels.put(3, 27);
        levels.put(4, 57);
        levels.put(5, 111);

        // TODO : ajouter les prochains niveaux
        return levels;
    }

    // --------------------------------

    public int getXp() {
        return this.xp;
    }

}