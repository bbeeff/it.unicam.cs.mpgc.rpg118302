package it.unicam.cs.mpgc.rpg118302.services.impl;

import it.unicam.cs.mpgc.rpg118302.models.dto.CharacterDto;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;
import it.unicam.cs.mpgc.rpg118302.models.enums.Gender;
import it.unicam.cs.mpgc.rpg118302.models.exception.RpgException;
import it.unicam.cs.mpgc.rpg118302.models.mapper.CharacterMapper;
import it.unicam.cs.mpgc.rpg118302.repositories.ICharacterRepository;
import it.unicam.cs.mpgc.rpg118302.services.ICharacterService;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del servizio di gestione dei personaggi.
 * Fornisce operazioni CRUD e validazione per i personaggi del gioco.
 */
public class CharacterServiceImpl implements ICharacterService {
    private final ICharacterRepository characterRepository;

    public CharacterServiceImpl(ICharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    /**
     * Crea un nuovo personaggio con validazione del nome e genere.
     * Salva il personaggio nel database e lo restituisce.
     * @param name il nome del personaggio
     * @param gender il genere del personaggio
     * @throws RpgException se il nome è vuoto o il genere è null
     */
    @Override
    public Character createCharacter(String name, Gender gender) throws RpgException {
        if (name == null || name.trim().isEmpty()) {
            throw new RpgException("Character name cannot be empty");
        }
        if (gender == null) {
            throw new RpgException("Gender must be selected");
        }
        Character character = new Character(name, gender);
        characterRepository.save(character);
        return character;
    }

    @Override
    public Optional<Character> getCharacter(Long id) {
        return characterRepository.findById(id);
    }

    @Override
    public Optional<Character> getCharacterByName(String name) {
        return characterRepository.findByName(name);
    }

    @Override
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    /**
     * Aggiorna un personaggio esistente nel database.
     * @param character il personaggio da aggiornare
     * @throws RpgException se il personaggio è null o non ha un ID valido
     */
    @Override
    public void updateCharacter(Character character) throws RpgException {
        if (character == null || character.getId() == null) {
            throw new RpgException("Invalid character");
        }
        characterRepository.update(character);
    }

    @Override
    public void deleteCharacter(Long id) {
        characterRepository.delete(id);
    }

    /**
     * Recupera un personaggio e lo converte in DTO usando il mapper.
     * @param id l'ID del personaggio da recuperare
     */
    @Override
    public CharacterDto getCharacterDto(Long id) {
        return getCharacter(id).map(CharacterMapper::toDto).orElse(null);
    }
}