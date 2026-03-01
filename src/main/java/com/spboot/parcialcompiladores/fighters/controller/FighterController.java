package com.spboot.parcialcompiladores.fighters.controller;

import com.spboot.parcialcompiladores.fighters.dto.FighterCreateDTO;
import com.spboot.parcialcompiladores.fighters.dto.FighterUpdateDTO;
import com.spboot.parcialcompiladores.fighters.model.Fighter;
import com.spboot.parcialcompiladores.fighters.service.FighterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fighters")
public class FighterController {

    private final FighterService service;

    public FighterController(FighterService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fighter create(@Valid @RequestBody FighterCreateDTO dto){
        return service.create(dto);
    }

    @GetMapping
    public List<Fighter> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Fighter findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Fighter update(@PathVariable Long id, @Valid @RequestBody FighterUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
