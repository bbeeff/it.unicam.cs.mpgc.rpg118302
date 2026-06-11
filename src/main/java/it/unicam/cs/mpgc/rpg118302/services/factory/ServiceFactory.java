package it.unicam.cs.mpgc.rpg118302.services.factory;

import it.unicam.cs.mpgc.rpg118302.repositories.ICharacterRepository;
import it.unicam.cs.mpgc.rpg118302.repositories.IBattleRepository;
import it.unicam.cs.mpgc.rpg118302.repositories.IEnemyRepository;
import it.unicam.cs.mpgc.rpg118302.repositories.impl.CharacterRepositoryImpl;
import it.unicam.cs.mpgc.rpg118302.repositories.impl.BattleRepositoryImpl;
import it.unicam.cs.mpgc.rpg118302.repositories.impl.EnemyRepositoryImpl;
import it.unicam.cs.mpgc.rpg118302.services.ICharacterService;
import it.unicam.cs.mpgc.rpg118302.services.IBattleService;
import it.unicam.cs.mpgc.rpg118302.services.impl.CharacterServiceImpl;
import it.unicam.cs.mpgc.rpg118302.services.impl.BattleServiceImpl;

/**
 * Factory per la creazione di istanze del servizio con inserimento delle dipendenze.
 * Utilizza il modello singleton per garantire istanze singole.
 */
public class ServiceFactory {
    private static ServiceFactory instance;
    private final ICharacterRepository characterRepository;
    private final IEnemyRepository enemyRepository;
    private final IBattleRepository battleRepository;
    private final ICharacterService characterService;
    private final IBattleService battleService;

    private ServiceFactory() {
        this.characterRepository = new CharacterRepositoryImpl();
        this.enemyRepository = new EnemyRepositoryImpl();
        this.battleRepository = new BattleRepositoryImpl();
        this.characterService = new CharacterServiceImpl(characterRepository);
        this.battleService = new BattleServiceImpl(battleRepository, enemyRepository);
    }

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public ICharacterService getCharacterService() {
        return characterService;
    }

    public IBattleService getBattleService() {
        return battleService;
    }

    public ICharacterRepository getCharacterRepository() {
        return characterRepository;
    }

    public IEnemyRepository getEnemyRepository() {
        return enemyRepository;
    }

    public IBattleRepository getBattleRepository() {
        return battleRepository;
    }
}
