package com.spboot.parcialcompiladores.fighters.mapper;

import com.spboot.parcialcompiladores.fighters.dto.FighterCreateDTO;
import com.spboot.parcialcompiladores.fighters.dto.FighterUpdateDTO;
import com.spboot.parcialcompiladores.fighters.model.Fighter;

public class FighterMapper {
    public static Fighter toEntity(FighterCreateDTO dto){
        Fighter f = new Fighter();
        f.setName(dto.name());
        f.setStrength(dto.strength());
        f.setSpeed(dto.speed());
        f.setStamina(dto.stamina());
        f.setSpecialSkill(dto.specialSkill());
        f.setBio(dto.bio());
        f.setBackstory(dto.backstory());
        f.setWins(0);
        f.setLosses(0);
        return f;
    }
    public static void applyUpdates(Fighter fighter, FighterUpdateDTO dto){
        fighter.setName(dto.name());
        fighter.setStrength(dto.strength());
        fighter.setSpeed(dto.speed());
        fighter.setStamina(dto.stamina());
        fighter.setSpecialSkill(dto.specialSkill());
        fighter.setBio(dto.bio());
        fighter.setBackstory(dto.backstory());
        fighter.setWins(dto.wins());
        fighter.setLosses(dto.losses());
    }
}
