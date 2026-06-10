package it.unicam.cs.mpgc.rpg118302.models.enums;

/**
 * Enum rappresenta lo stato della battaglia.
 */
public enum BattleStatus {
    IN_PROGRESS("In corso"),
    PLAYER_WON("Vittoria"),
    PLAYER_LOST("Sconfitta"),
    FLED("Fuga");

    private final String displayName;

    BattleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
