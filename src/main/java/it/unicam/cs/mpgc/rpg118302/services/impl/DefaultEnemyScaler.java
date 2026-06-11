package it.unicam.cs.mpgc.rpg118302.services.impl;

import it.unicam.cs.mpgc.rpg118302.models.entities.Enemy;
import it.unicam.cs.mpgc.rpg118302.services.IEnemyScaler;

/**
 * Implementazione della strategia di scalatura logaritmica dei nemici.
 */
public class DefaultEnemyScaler implements IEnemyScaler {

    /**
     * Scala le statistiche del nemico in base al livello del personaggio.
     * La scalatura è logaritmica per evitare squilibri:
     * livello 2 = 1.3x, livello 3 = 1.6x.
     * Attacco e difesa crescono meno della salute per mantenere il gioco bilanciato.
     */
    @Override
    public void scaleEnemyStats(Enemy enemy, int playerLevel) {
        double multiplier = 1.0 + (playerLevel - 1) * 0.3;

        enemy.setMaxHealth((int)(enemy.getMaxHealth() * multiplier));
        enemy.setCurrentHealth(enemy.getMaxHealth());
        enemy.setAttack((int)(enemy.getAttack() * multiplier * 0.8));
        enemy.setDefense((int)(enemy.getDefense() * multiplier * 0.6));
        enemy.setExperienceReward((int)(enemy.getExperienceReward() * multiplier));
    }
}