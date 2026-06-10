package it.unicam.cs.mpgc.rpg118302.repositories.impl;

import it.unicam.cs.mpgc.rpg118302.config.JpaUtil;
import it.unicam.cs.mpgc.rpg118302.models.entities.Battle;
import it.unicam.cs.mpgc.rpg118302.repositories.IBattleRepository;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del repository di battaglia utilizzando JPA.
 */
public class BattleRepositoryImpl extends BaseRepository<Battle, Long> implements IBattleRepository {
    public BattleRepositoryImpl() {
        super(Battle.class);
        this.setEntityManager(JpaUtil.getEntityManager());
    }

    @Override
    public Optional<Battle> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<Battle> findAll() {
        TypedQuery<Battle> query = entityManager.createQuery("SELECT b FROM Battle b", Battle.class);
        return query.getResultList();
    }

    @Override
    public List<Battle> findByCharacterId(Long characterId) {
        TypedQuery<Battle> query = entityManager.createQuery(
                "SELECT b FROM Battle b WHERE b.character.id = :characterId", Battle.class);
        query.setParameter("characterId", characterId);
        return query.getResultList();
    }
}