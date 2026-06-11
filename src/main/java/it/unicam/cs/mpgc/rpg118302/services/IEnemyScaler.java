package it.unicam.cs.mpgc.rpg118302.services;

import it.unicam.cs.mpgc.rpg118302.models.entities.Enemy;

/**
 * Interfaccia che definisce la strategia di scalatura delle statistiche nemici
 * in base al livello del giocatore.
 */
public interface IEnemyScaler {
    /**
     * Scala le statistiche del nemico in base al livello del personaggio.
     */
    void scaleEnemyStats(Enemy enemy, int playerLevel);
}