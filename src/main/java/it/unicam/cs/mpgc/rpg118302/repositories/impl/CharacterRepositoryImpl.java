package it.unicam.cs.mpgc.rpg118302.repositories.impl;

import it.unicam.cs.mpgc.rpg118302.config.JpaUtil;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.repositories.ICharacterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del repository di caratteri utilizzando JPA.
 */
public class CharacterRepositoryImpl extends BaseRepository<Character, Long> implements ICharacterRepository {
    public CharacterRepositoryImpl() {
        super(Character.class);
        this.setEntityManager(JpaUtil.getEntityManager());
    }

    @Override
    public Optional<Character> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<Character> findAll() {
        TypedQuery<Character> query = entityManager.createQuery("SELECT c FROM Character c", Character.class);
        return query.getResultList();
    }

    @Override
    public Optional<Character> findByName(String name) {
        TypedQuery<Character> query = entityManager.createQuery(
                "SELECT c FROM Character c WHERE c.name = :name", Character.class);
        query.setParameter("name", name);
        List<Character> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}