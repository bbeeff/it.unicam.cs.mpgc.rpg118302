package it.unicam.cs.mpgc.rpg118302.models.mapper;

import it.unicam.cs.mpgc.rpg118302.models.dto.EnemyDto;
import it.unicam.cs.mpgc.rpg118302.models.entities.Enemy;

/**
 * Mapper per entità carattere e conversione DTO.
 */
public class EnemyMapper {
    public static EnemyDto toDto(Enemy enemy) {
        if (enemy == null) {
            return null;
        }
        return new EnemyDto(
                enemy.getId(),
                enemy.getType(),
                enemy.getCurrentHealth(),
                enemy.getMaxHealth(),
                enemy.getAttack(),
                enemy.getDefense()
        );
    }

    public static Enemy toEntity(EnemyDto dto) {
        if (dto == null) {
            return null;
        }
        Enemy enemy = new Enemy(dto.getType());
        enemy.setId(dto.getId());
        enemy.setCurrentHealth(dto.getCurrentHealth());
        enemy.setMaxHealth(dto.getMaxHealth());
        enemy.setAttack(dto.getAttack());
        enemy.setDefense(dto.getDefense());
        return enemy;
    }
}
