package it.unicam.cs.mpgc.rpg118302.models.exception;

/**
 * Eccezione di base per errori
 */
public class RpgException extends Exception {
    public RpgException(String message) {
        super(message);
    }

    public RpgException(String message, Throwable cause) {
        super(message, cause);
    }
}
