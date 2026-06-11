package it.unicam.cs.mpgc.rpg118302.services;

import it.unicam.cs.mpgc.rpg118302.models.entities.Battle;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleAction;
import it.unicam.cs.mpgc.rpg118302.models.enums.BattleStatus;
import it.unicam.cs.mpgc.rpg118302.models.exception.RpgException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia di servizio per operazioni e logica di battaglia.
 */
public interface IBattleService {
    Battle initiateBattle(Character character) throws RpgException;
    String executeAction(Battle battle, BattleAction action) throws RpgException;
    Optional<Battle> getBattle(Long id);
    List<Battle> getCharacterBattles(Long characterId);
    BattleStatus getBattleStatus(Battle battle);
    void saveBattle(Battle battle);
}
