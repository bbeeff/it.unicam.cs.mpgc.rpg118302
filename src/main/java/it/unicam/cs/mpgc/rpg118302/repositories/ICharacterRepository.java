package it.unicam.cs.mpgc.rpg118302.repositories;

import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia repository per le operazioni sulle entità carattere.
 */
public interface ICharacterRepository {
    void save(Character character);
    void update(Character character);
    void delete(Long id);
    Optional<Character> findById(Long id);
    List<Character> findAll();
    Optional<Character> findByName(String name);
}
