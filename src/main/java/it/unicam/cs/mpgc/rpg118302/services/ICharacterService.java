package it.unicam.cs.mpgc.rpg118302.services;

import it.unicam.cs.mpgc.rpg118302.models.dto.CharacterDto;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;
import it.unicam.cs.mpgc.rpg118302.models.exception.RpgException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia di servizio per le operazioni sui caratteri.
 */
public interface ICharacterService {
    Character createCharacter(String name, Gender gender) throws RpgException;
    Optional<Character> getCharacter(Long id);
    Optional<Character> getCharacterByName(String name);
    List<Character> getAllCharacters();
    void updateCharacter(Character character) throws RpgException;
    void deleteCharacter(Long id);
    CharacterDto getCharacterDto(Long id);
}
