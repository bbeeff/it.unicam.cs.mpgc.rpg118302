package it.unicam.cs.mpgc.rpg118302.models.enums;

/**
 * Enum rappresenta i tipi.
 */
public enum ItemType {
    HEALTH_POTION("Pozione Vita", 30),
    MANA_POTION("Pozione Mana", 20),
    STRENGTH_POTION("Pozione Forza", 10);

    private final String displayName;
    private final int value;

    ItemType(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getValue() {
        return value;
    }
}
