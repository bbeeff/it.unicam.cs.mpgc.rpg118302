package it.unicam.cs.mpgc.rpg118302.models.mapper;

import it.unicam.cs.mpgc.rpg118302.models.dto.CharacterDto;
import it.unicam.cs.mpgc.rpg118302.models.entities.Character;

/**
 * Mapper per entità carattere e conversione DTO.
 */
public class CharacterMapper {
    public static CharacterDto toDto(Character character) {
        if (character == null) {
            return null;
        }
        return new CharacterDto(
                character.getId(),
                character.getName(),
                character.getGender(),
                character.getCurrentHealth(),
                character.getMaxHealth(),
                character.getAttack(),
                character.getDefense(),
                character.getPotions(),
                character.getLevel(),
                character.getExperience()
        );
    }

    public static Character toEntity(CharacterDto dto) {
        if (dto == null) {
            return null;
        }
        Character character = new Character(dto.getName(), dto.getGender());
        character.setId(dto.getId());
        character.setCurrentHealth(dto.getCurrentHealth());
        character.setMaxHealth(dto.getMaxHealth());
        character.setAttack(dto.getAttack());
        character.setDefense(dto.getDefense());
        character.setPotions(dto.getPotions());
        character.setLevel(dto.getLevel());
        character.setExperience(dto.getExperience());
        return character;
    }
}
