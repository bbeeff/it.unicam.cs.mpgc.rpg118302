package it.unicam.cs.mpgc.rpg118302.models.entities;

import it.unicam.cs.mpgc.rpg118302.models.enums.EnemyType;
import jakarta.persistence.*;

/**
 * Entità che rappresenta un nemico nel dungeon.
 */
@Entity
@Table(name = "enemies")
public class Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnemyType type;

    @Column(nullable = false)
    private int currentHealth;

    @Column(nullable = false)
    private int maxHealth;

    @Column(nullable = false)
    private int attack;

    @Column(nullable = false)
    private int defense;

    @Column(nullable = false)
    private int experienceReward;

    public Enemy() {
    }

    /**
     * Costruttore per creare un nemico di un tipo specifico con statistiche iniziali.
     */
    public Enemy(EnemyType type) {
        this.type = type;
        this.maxHealth = type.getHealth();
        this.currentHealth = type.getHealth();
        this.attack = type.getAttack();
        this.defense = type.getDefense();
        this.experienceReward = type.getHealth() * 2;
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

    /**
     * Imposta la salute attuale limitata dalla salute massima.
     */
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.min(currentHealth, maxHealth);
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

    public int getExperienceReward() {
        return experienceReward;
    }

    public void setExperienceReward(int experienceReward) {
        this.experienceReward = experienceReward;
    }

    /**
     * Verifica se il nemico è ancora vivo.
     */
    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * Riduce la salute del nemico per il danno specificato.
     * La salute non può scendere al di sotto di 0.
     */
    public void takeDamage(int damage) {
        this.currentHealth = Math.max(0, currentHealth - damage);
    }

    /**
     * Ottiene il nome del nemico dal suo tipo.
     */
    public String getName() {
        return type.getEnemyName();
    }
}