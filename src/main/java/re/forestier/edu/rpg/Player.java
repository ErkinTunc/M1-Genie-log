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

    public Player(
            String playerName,
            String avatarName,
            String avatarClass,
            int gameMoney,
            ArrayList<String> inventory) {

        if (!CharacterRules.isValidAvatarClass(avatarClass)) {
            return;
        }

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = avatarClass;
        this.gameMoney = gameMoney;
        this.inventory = inventory;
        this.abilities = CharacterRules.getInitialAbilities(avatarClass);
    }

    // ----------- Public Methods ----------------

    public void removeGameMoney(int amount) throws IllegalArgumentException {
        if (gameMoney - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }

        gameMoney = gameMoney - amount;
    }

    public void addGameMoney(int amount) {
        gameMoney = gameMoney + amount;
    }

    // ------- Getters ----------

    public String getAvatarClass() {
        return avatarClass;
    }

    public int getLevel() {
        int maxLevel = 5;
        for (int level = 2; level <= maxLevel; level++) {
            if (xp < calculateXpThreshold(level)) {
                return level - 1;
            }
        }
        // TODO : ajouter les prochains niveaux

        return maxLevel;
    }

    public int getXp() {
        return this.xp;
    }

    // ----------- Private helpers ---------------

    private static int calculateXpThreshold(int level) {
        // XP threshold formula (integer division):
        // XP(n) = (n - 1) * 10 + (n * XP(n - 1)) / 4
        int threshold = 0;

        for (int i = 2; i <= level; i++) {
            threshold = (i - 1) * 10
                    + (i * threshold) / 4;
        }

        return threshold;
    }

}