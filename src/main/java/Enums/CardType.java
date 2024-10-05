package Enums;

public enum CardType {

    // Weapon Cards
    DAGGER("Weapon", 5),
    HORSE("Weapon", 10),
    SWORD("Weapon", 10),
    BATTLE_AXE("Weapon", 15),
    LANCE("Weapon", 20),
    EXCALIBUR("Weapon", 30),

    // Foe Cards
    F5("Foe", 5),
    F10("Foe", 10),
    F15("Foe", 15),
    F20("Foe", 20),
    F25("Foe", 25),
    F30("Foe", 30),
    F35("Foe", 35),
    F40("Foe", 40),
    F50("Foe", 50),
    F70("Foe", 70),

    // Quest Cards
    Q2("Quest", 2),
    Q3("Quest", 3),
    Q4("Quest", 4),
    Q5("Quest", 5),

    // Event Cards
    PLAGUE("Event"),
    QUEENS_FAVOR("Event"),
    PROSPERITY("Event");

    // Fields for common attributes
    private final String type;
    private final int value;

    // Constructor for cards with value or stages
    CardType(String type, int value) {
        this.type = type;
        this.value = value;
    }

    // Constructor for event cards without a value
    CardType(String category) {
        this.type = category;
        this.value = 0; // Default value for cards without specific numeric values
    }

    public int getValue() {
        return value;
    }

    public boolean isWeapon() {
        return type.equals("Weapon");
    }

    public boolean isFoe() {
        return type.equals("Foe");
    }

    public boolean isQuest() {
        return type.equals("Quest");
    }

    public boolean isEvent() {
        return type.equals("Event");
    }
}


