package com.spboot.parcialcompiladores.fighters.service;

import com.spboot.parcialcompiladores.excepciones.NotFoundException;
import com.spboot.parcialcompiladores.fighters.dto.FighterCreateDTO;
import com.spboot.parcialcompiladores.fighters.dto.FighterUpdateDTO;
import com.spboot.parcialcompiladores.fighters.mapper.FighterMapper;
import com.spboot.parcialcompiladores.fighters.model.Fighter;
import com.spboot.parcialcompiladores.fighters.repository.FighterRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FighterService {

    private final FighterRepository repo;

    public FighterService(FighterRepository repo) {
        this.repo = repo;
    }

    public Fighter create(FighterCreateDTO dto){
        return repo.save(FighterMapper.toEntity(dto));
    }

    public List<Fighter> findAll(){
        return repo.findAll();
    }

    public Fighter findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Fighter with id " + id + " not found"));
    }

    public Fighter update(Long id, FighterUpdateDTO dto){
        Fighter fighter = findById(id);
        FighterMapper.applyUpdates(fighter, dto);
        return repo.save(fighter);
    }

    public void delete(Long id){
        Fighter fighter = findById(id);
        repo.delete(fighter);
    }
}
