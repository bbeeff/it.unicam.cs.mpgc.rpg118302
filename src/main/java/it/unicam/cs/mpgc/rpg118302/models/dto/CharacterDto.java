package it.unicam.cs.mpgc.rpg118302.models.dto;

import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;

/**
 * Data transfer object for Character.
 */
public class CharacterDto {
    private Long id;
    private String name;
    private Gender gender;
    private int currentHealth;
    private int maxHealth;
    private int attack;
    private int defense;
    private int potions;
    private int level;
    private int experience;

    public CharacterDto() {
    }

    public CharacterDto(Long id, String name, Gender gender, int currentHealth, int maxHealth,
                        int attack, int defense, int potions, int level, int experience) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.potions = potions;
        this.level = level;
        this.experience = experience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public int getPotions() {
        return potions;
    }

    public void setPotions(int potions) {
        this.potions = potions;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
