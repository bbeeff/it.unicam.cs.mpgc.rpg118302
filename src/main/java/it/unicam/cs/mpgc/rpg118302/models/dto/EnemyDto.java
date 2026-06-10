package it.unicam.cs.mpgc.rpg118302.models.dto;

import it.unicam.cs.mpgc.rpg118302.models.enums.EnemyType;

/**
 * Data transfer object for Enemy.
 */
public class EnemyDto {
    private Long id;
    private EnemyType type;
    private int currentHealth;
    private int maxHealth;
    private int attack;
    private int defense;

    public EnemyDto() {
    }

    public EnemyDto(Long id, EnemyType type, int currentHealth, int maxHealth, int attack, int defense) {
        this.id = id;
        this.type = type;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnemyType getType() {
        return type;
    }

    public void setType(EnemyType type) {
        this.type = type;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
