package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class player {
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

    // ----------- Constructor  ----------------

    public player(String playerName, String avatarName, String avatarClass, int money, ArrayList<String> inventory) {
        if (!avatarClass.equals("ARCHER") && !avatarClass.equals("ADVENTURER") && !avatarClass.equals("DWARF")) {
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
        var value = Integer.valueOf(amount);
        gameMoney = gameMoney + (value != null ? value : 0);
    }

    // ----------- Getters ----------------

    public String getAvatarClass() {
        return avatarClass;
    }

    public int getLevel() {
        // (lvl-1) * 10 + round((lvl * xplvl-1)/4)
        HashMap<Integer, Integer> levels = new HashMap<>();
        levels.put(2, 10); // 1*10 + ((2*0)/4)
        levels.put(3, 27); // 2*10 + ((3*10)/4)
        levels.put(4, 57); // 3*10 + ((4*27)/4)
        levels.put(5, 111); // 4*10 + ((5*57)/4)
        // TODO : ajouter les prochains niveaux

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

    public int getXp() {
        return this.xp;
    }

    /*
     * Ингредиенты:
     * Для теста:
     * 
     * 250 г муки
     * 125 г сливочного масла (холодное)
     * 70 г сахара
     * 1 яйцо
     * 1 щепотка соли
     */

}