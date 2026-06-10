package it.unicam.cs.mpgc.rpg118302.models.entities;

import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entità che rappresenta il personaggio del giocatore nel dungeon RPG.
 * Gestisce le statistiche del personaggio, la progressione e la meccanica di battaglia.
 */
@Entity
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private int maxHealth;

    @Column(nullable = false)
    private int currentHealth;

    @Column(nullable = false)
    private int attack;

    @Column(nullable = false)
    private int defense;

    @Column(nullable = false)
    private int potions;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int experience;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Costruttore predefinito per JPA.
     */
    public Character() {
    }

    /**
     * Costruttore per creare un nuovo personaggio con statistiche iniziali.
     */
    public Character(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.maxHealth = 100;
        this.currentHealth = 100;
        this.attack = 10;
        this.defense = 5;
        this.potions = 3;
        this.level = 1;
        this.experience = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Ottiene l'identificativo univoco del personaggio.
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'identificativo univoco del personaggio.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ottiene il nome del personaggio.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del personaggio.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ottiene il genere del personaggio.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Imposta il genere del personaggio.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Ottiene la salute massima del personaggio.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Imposta la salute massima del personaggio.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Ottiene la salute attuale del personaggio.
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Imposta la salute attuale del personaggio, limitata dalla salute massima.
     */
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.min(currentHealth, maxHealth);
    }

    /**
     * Ottiene il potere di attacco del personaggio.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Imposta il potere di attacco del personaggio.
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Ottiene il potere di difesa del personaggio.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Imposta il potere di difesa del personaggio.
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * Ottiene il numero di pozioni che il personaggio possiede.
     */
    public int getPotions() {
        return potions;
    }

    /**
     * Imposta il numero di pozioni.
     */
    public void setPotions(int potions) {
        this.potions = potions;
    }

    /**
     * Ottiene il livello attuale del personaggio.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Imposta il livello del personaggio.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Ottiene i punti esperienza totali del personaggio.
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Imposta i punti esperienza del personaggio.
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Ottiene il timestamp di creazione.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Imposta il timestamp di creazione.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Ottiene il timestamp dell'ultimo aggiornamento.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Imposta il timestamp dell'ultimo aggiornamento.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Verifica se il personaggio è ancora vivo.
     */
    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * Riduce la salute del personaggio per il danno specificato.
     * La salute non può scendere al di sotto di 0.
     */
    public void takeDamage(int damage) {
        this.currentHealth = Math.max(0, currentHealth - damage);
    }

    /**
     * Aumenta la salute del personaggio della quantità specificata.
     * La salute non può superare la salute massima.
     */
    public void heal(int amount) {
        this.currentHealth = Math.min(maxHealth, currentHealth + amount);
    }

    /**
     * Usa una pozione per ripristinare 30 punti salute.
     * Diminuisce il conteggio delle pozioni di 1 se disponibili.
     */
    public void usePotion() {
        if (potions > 0) {
            potions--;
            heal(30);
        }
    }

    /**
     * Aumenta il livello del personaggio e le sue statistiche.
     * Il livello aumenta di 1 e le statistiche vengono potenziate di conseguenza.
     */
    public void levelUp() {
        this.level++;
        this.maxHealth += 25;
        this.currentHealth = this.maxHealth;
        this.attack += 8;
        this.defense += 3;
        this.potions += 1;
    }
}