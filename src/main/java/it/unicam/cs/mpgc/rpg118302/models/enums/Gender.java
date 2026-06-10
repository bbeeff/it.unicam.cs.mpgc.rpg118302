package it.unicam.cs.mpgc.rpg118302.models.enums;

/**
 * Enum rappresenta il sesso del player.
 */
public enum Gender {
    MALE("Maschio"),
    FEMALE("Femmina");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
