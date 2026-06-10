package it.unicam.cs.mpgc.rpg118302.repositories;

import it.unicam.cs.mpgc.rpg118302.models.entities.Battle;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia repository per le operazioni delle entità Battle.
 */
public interface IBattleRepository {
    void save(Battle battle);
    void update(Battle battle);
    void delete(Long id);
    Optional<Battle> findById(Long id);
    List<Battle> findAll();
    List<Battle> findByCharacterId(Long characterId);
}
