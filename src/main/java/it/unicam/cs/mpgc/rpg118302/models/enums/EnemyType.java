package it.unicam.cs.mpgc.rpg118302.models.enums;

/**
 * Enum rappresenta i diversi nemici con lo stato
 */
public enum EnemyType {
    GOBLIN("Goblin", 20, 5, 15),
    ORC("Orco", 40, 8, 25),
    CENTAUR("Centauro", 60, 12, 40),
    DEVIL("Diavolo", 80, 15, 55),
    DRAGON("Drago", 120, 20, 85);

    private final String name;
    private final int health;
    private final int attack;
    private final int defense;

    EnemyType(String name, int health, int attack, int defense) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public String getEnemyName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}
