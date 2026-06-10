package it.unicam.cs.mpgc.rpg118302.models.enums;

/**
 * Enum rappresenta la variabile azione durante la battaglia
 */
public enum BattleAction {
    ATTACK("Attacca"),
    DEFEND("Difendi"),
    USE_POTION("Usa Pozione"),
    FLEE("Fuggi");

    private final String displayName;

    BattleAction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
