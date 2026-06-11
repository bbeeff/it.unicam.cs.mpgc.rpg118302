package it.unicam.cs.mpgc.rpg118302.services.factory;

import it.unicam.cs.mpgc.rpg118302.repositories.IBattleRepository;
import it.unicam.cs.mpgc.rpg118302.repositories.ICharacterRepository;
import it.unicam.cs.mpgc.rpg118302.repositories.IEnemyRepository;
import it.unicam.cs.mpgc.rpg118302.repositories.impl.BattleRepositoryImpl;
import it.unicam.cs.mpgc.rpg118302.repositories.impl.CharacterRepositoryImpl;
import it.unicam.cs.mpgc.rpg118302.repositories.impl.EnemyRepositoryImpl;
import it.unicam.cs.mpgc.rpg118302.services.IBattleService;
import it.unicam.cs.mpgc.rpg118302.services.ICharacterService;
import it.unicam.cs.mpgc.rpg118302.services.IEnemyScaler;
import it.unicam.cs.mpgc.rpg118302.services.impl.BattleServiceImpl;
import it.unicam.cs.mpgc.rpg118302.services.impl.CharacterServiceImpl;
import it.unicam.cs.mpgc.rpg118302.services.impl.DefaultEnemyScaler;

/**
 * Factory per la creazione e la gestione dei servizi.
 * Implementa il pattern Singleton e fornisce l'iniezione manuale delle dipendenze.
 */
public class ServiceFactory {
    private static ServiceFactory instance;

    private ICharacterRepository characterRepository;
    private IEnemyRepository enemyRepository;
    private IBattleRepository battleRepository;

    private ICharacterService characterService;
    private IBattleService battleService;
    private IEnemyScaler enemyScaler;

    private ServiceFactory() {
        initializeRepositories();
        initializeServices();
    }

    /**
     * Ottiene l'istanza singleton della factory.
     */
    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    private void initializeRepositories() {
        this.characterRepository = new CharacterRepositoryImpl();
        this.enemyRepository = new EnemyRepositoryImpl();
        this.battleRepository = new BattleRepositoryImpl();
    }

    private void initializeServices() {
        this.characterService = new CharacterServiceImpl(characterRepository);
        this.battleService = new BattleServiceImpl(battleRepository, enemyRepository);
        this.enemyScaler = new DefaultEnemyScaler();
    }

    public ICharacterService getCharacterService() {
        return characterService;
    }

    public IBattleService getBattleService() {
        return battleService;
    }

    /**
     * Ottiene la strategia di scalatura dei nemici.
     */
    public IEnemyScaler getEnemyScaler() {
        return enemyScaler;
    }
}