package it.unicam.cs.mpgc.rpg118302.services.impl;

import it.unicam.cs.mpgc.rpg118302.models.entities.Battle;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.entities.Enemy;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleAction;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleStatus;
import it.unicam.cs.mpgc.rpg118302.models.enums.EnemyType;
import it.unicam.cs.mpgc.rpg118302.models.exception.RpgException;
import it.unicam.cs.mpgc.rpg118302.repositories.IBattleRepository;
import it.unicam.cs.mpgc.rpg118302.repositories.IEnemyRepository;
import it.unicam.cs.mpgc.rpg118302.services.IBattleService;
import it.unicam.cs.mpgc.rpg118302.services.IEnemyScaler;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Implementazione del servizio di battaglia con logica a turni.
 * Gestisce l'inizializzazione, l'esecuzione e la conclusione delle battaglie.
 */
public class BattleServiceImpl implements IBattleService {
    private final IBattleRepository battleRepository;
    private final IEnemyRepository enemyRepository;
    private final IEnemyScaler enemyScaler;
    private final Random random = new Random();
    private boolean isPlayerDefending = false;

    public BattleServiceImpl(IBattleRepository battleRepository, IEnemyRepository enemyRepository) {
        this.battleRepository = battleRepository;
        this.enemyRepository = enemyRepository;
        this.enemyScaler = new DefaultEnemyScaler();
    }

    /**
     * Inizia una nuova battaglia creando un nemico casuale ma scalato
     * in base al livello del personaggio. Il nemico è salvato nel database
     * e la battaglia viene registrata come IN_PROGRESS.
     */
    @Override
    public Battle initiateBattle(Character character) throws RpgException {
        if (character == null || !character.isAlive()) {
            throw new RpgException("Invalid or dead character");
        }

        EnemyType[] allEnemies = EnemyType.values();
        EnemyType selectedType = allEnemies[Math.min(character.getLevel() - 1, allEnemies.length - 1)];

        Enemy enemy = new Enemy(selectedType);
        scaleEnemyStats(enemy, character.getLevel());
        enemyRepository.save(enemy);

        Battle battle = new Battle(character, enemy);
        battleRepository.save(battle);
        return battle;
    }

    /**
     * Esegue un'azione durante una battaglia a turni. Gestisce attacco, difesa,
     * uso pozioni e fuga. Dopo l'azione del giocatore, il nemico contrattacca
     * (se vivo). Verifica la fine della battaglia e aggiorna il database.
     */
    @Override
    public String executeAction(Battle battle, BattleAction action) throws RpgException {
        if (battle == null || battle.getStatus() != BattleStatus.IN_PROGRESS) {
            throw new RpgException("Battle is not in progress");
        }

        Character character = battle.getCharacter();
        Enemy enemy = battle.getEnemy();
        StringBuilder result = new StringBuilder();

        switch (action) {
            case ATTACK:
                isPlayerDefending = false;
                int damage = calculateDamage(character.getAttack(), enemy.getDefense());
                enemy.takeDamage(damage);
                result.append("Attacchi il ").append(enemy.getName()).append(" per ").append(damage).append(" danni.\n");
                break;
            case DEFEND:
                isPlayerDefending = true;
                result.append("Ti prepari a difenderti!\n");
                break;
            case USE_POTION:
                if (character.getPotions() > 0) {
                    character.usePotion();
                    result.append("Usi una pozione e guarisci 30 punti vita.\n");
                } else {
                    result.append("Non hai pozioni disponibili!\n");
                }
                break;
            case FLEE:
                if (random.nextDouble() < 0.5) {
                    battle.setStatus(BattleStatus.FLED);
                    result.append("Sei riuscito a fuggire!\n");
                    battle.recordEnd();
                    battleRepository.update(battle);
                    return result.toString();
                } else {
                    result.append("Non sei riuscito a fuggire!\n");
                }
                break;
        }

        if (enemy.isAlive() && action != BattleAction.FLEE) {
            int enemyDamage = calculateDamage(enemy.getAttack(),
                    character.getDefense() + (isPlayerDefending ? 5 : 0));
            character.takeDamage(enemyDamage);
            result.append("Il ").append(enemy.getName()).append(" ti attacca per ").append(enemyDamage).append(" danni.\n");
            isPlayerDefending = false;
        }

        battle.setTurnsCount(battle.getTurnsCount() + 1);
        if (!character.isAlive()) {
            battle.setStatus(BattleStatus.PLAYER_LOST);
            result.append("\nSei stato sconfitto!");
        } else if (!enemy.isAlive()) {
            battle.setStatus(BattleStatus.PLAYER_WON);
            character.setExperience(character.getExperience() + enemy.getExperienceReward());
            character.levelUp();
            result.append("\nHai sconfitto il ").append(enemy.getName()).append("! Guadagni ")
                    .append(enemy.getExperienceReward()).append(" esperienza!")
                    .append("\n[Livello: ").append(character.getLevel()).append(" | Attacco: ")
                    .append(character.getAttack()).append(" | Vita: ").append(character.getCurrentHealth())
                    .append("/").append(character.getMaxHealth()).append("]");
        }

        if (battle.getStatus() != BattleStatus.IN_PROGRESS) {
            battle.recordEnd();
        }

        battleRepository.update(battle);
        return result.toString();
    }

    @Override
    public Optional<Battle> getBattle(Long id) {
        return battleRepository.findById(id);
    }

    @Override
    public List<Battle> getCharacterBattles(Long characterId) {
        return battleRepository.findByCharacterId(characterId);
    }

    @Override
    public BattleStatus getBattleStatus(Battle battle) {
        return battle.getStatus();
    }

    @Override
    public void saveBattle(Battle battle) {
        battleRepository.update(battle);
    }

    /**
     * Calcola il danno inflitto considerando attacco e difesa.
     * Garantisce almeno il 40% dell'attacco come danno minimo,
     * anche con difesa elevata. Aggiunge variance casuale per equilibrio.
     */
    private int calculateDamage(int attack, int defense) {
        int baseDamage = Math.max(attack * 40 / 100, attack - (defense / 4));
        int variance = random.nextInt(5) - 1;
        return Math.max(3, baseDamage + variance);
    }

    /**
     * Scala le statistiche del nemico usando la strategia iniettata.
     */
    private void scaleEnemyStats(Enemy enemy, int playerLevel) {
        enemyScaler.scaleEnemyStats(enemy, playerLevel);
    }
}