package it.unicam.cs.mpgc.rpg118302.models.entities;

import it.unicam.cs.mpgc.rpg118302.models.enums.BattleStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entità che rappresenta una sessione di battaglia nel dungeon.
 */
@Entity
@Table(name = "battles")
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne
    @JoinColumn(name = "enemy_id", nullable = false)
    private Enemy enemy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BattleStatus status;

    @Column(nullable = false)
    private int playerHealthStart;

    @Column(nullable = false)
    private int playerHealthEnd;

    @Column(nullable = false)
    private int enemyHealthStart;

    @Column(nullable = false)
    private int enemyHealthEnd;

    @Column(nullable = false)
    private int turnsCount;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    public Battle() {
    }

    /**
     * Costruttore per iniziare una nuova battaglia tra personaggio e nemico.
     * Registra la salute iniziale di entrambi e il timestamp di inizio.
     */
    public Battle(Character character, Enemy enemy) {
        this.character = character;
        this.enemy = enemy;
        this.status = BattleStatus.IN_PROGRESS;
        this.playerHealthStart = character.getCurrentHealth();
        this.enemyHealthStart = enemy.getCurrentHealth();
        this.turnsCount = 0;
        this.startedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public BattleStatus getStatus() {
        return status;
    }

    public void setStatus(BattleStatus status) {
        this.status = status;
    }

    public int getPlayerHealthStart() {
        return playerHealthStart;
    }

    public void setPlayerHealthStart(int playerHealthStart) {
        this.playerHealthStart = playerHealthStart;
    }

    public int getPlayerHealthEnd() {
        return playerHealthEnd;
    }

    public void setPlayerHealthEnd(int playerHealthEnd) {
        this.playerHealthEnd = playerHealthEnd;
    }

    public int getEnemyHealthStart() {
        return enemyHealthStart;
    }

    public void setEnemyHealthStart(int enemyHealthStart) {
        this.enemyHealthStart = enemyHealthStart;
    }

    public int getEnemyHealthEnd() {
        return enemyHealthEnd;
    }

    public void setEnemyHealthEnd(int enemyHealthEnd) {
        this.enemyHealthEnd = enemyHealthEnd;
    }

    public int getTurnsCount() {
        return turnsCount;
    }

    public void setTurnsCount(int turnsCount) {
        this.turnsCount = turnsCount;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    /**
     * Registra lo stato finale della battaglia con la salute attuale di entrambi
     * e il timestamp di fine. Chiamato quando la battaglia termina.
     */
    public void recordEnd() {
        this.playerHealthEnd = character.getCurrentHealth();
        this.enemyHealthEnd = enemy.getCurrentHealth();
        this.endedAt = LocalDateTime.now();
    }
}