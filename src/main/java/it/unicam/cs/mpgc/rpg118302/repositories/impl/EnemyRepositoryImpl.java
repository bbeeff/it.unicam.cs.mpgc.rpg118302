package it.unicam.cs.mpgc.rpg118302.repositories.impl;

import it.unicam.cs.mpgc.rpg118302.config.JpaUtil;
import it.unicam.cs.mpgc.rpg118302.models.entities.Enemy;
import it.unicam.cs.mpgc.rpg118302.models.enums.EnemyType;
import it.unicam.cs.mpgc.rpg118302.repositories.IEnemyRepository;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del repository utilizzando JPA.
 */
public class EnemyRepositoryImpl extends BaseRepository<Enemy, Long> implements IEnemyRepository {
    public EnemyRepositoryImpl() {
        super(Enemy.class);
        this.setEntityManager(JpaUtil.getEntityManager());
    }

    @Override
    public Optional<Enemy> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<Enemy> findAll() {
        TypedQuery<Enemy> query = entityManager.createQuery("SELECT e FROM Enemy e", Enemy.class);
        return query.getResultList();
    }

    @Override
    public List<Enemy> findByType(EnemyType type) {
        TypedQuery<Enemy> query = entityManager.createQuery(
                "SELECT e FROM Enemy e WHERE e.type = :type", Enemy.class);
        query.setParameter("type", type);
        return query.getResultList();
    }
}