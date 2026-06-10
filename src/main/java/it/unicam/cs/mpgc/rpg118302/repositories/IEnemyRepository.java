package it.unicam.cs.mpgc.rpg118302.repositories;

import it.unicam.cs.mpgc.rpg118302.models.entities.Enemy;
import it.unicam.cs.mpgc.rpg118302.models.enums.EnemyType;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia repository per le operazioni delle entità nemiche.
 */
public interface IEnemyRepository {
    void save(Enemy enemy);
    void update(Enemy enemy);
    void delete(Long id);
    Optional<Enemy> findById(Long id);
    List<Enemy> findAll();
    List<Enemy> findByType(EnemyType type);
}
