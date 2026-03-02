package com.spboot.parcialcompiladores.fights.controller;

import com.spboot.parcialcompiladores.fights.dto.FightCreateDTO;
import com.spboot.parcialcompiladores.fights.dto.FightExecuteDTO;
import com.spboot.parcialcompiladores.fights.dto.FightExecuteResponseDTO;
import com.spboot.parcialcompiladores.fights.dto.FightUpdateDTO;
import com.spboot.parcialcompiladores.fights.model.Fight;
import com.spboot.parcialcompiladores.fights.service.FightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fights")
public class FightController {

    private final FightService fightService;

    public FightController(FightService fightService) {
        this.fightService = fightService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fight create(@Valid @RequestBody FightCreateDTO dto) {
        return fightService.create(dto);
    }

    @PostMapping("/{id}/execute")
    public FightExecuteResponseDTO execute(@PathVariable Long id, @Valid @RequestBody FightExecuteDTO dto) {
        Fight fight = fightService.execute(id, dto);
        return new FightExecuteResponseDTO(
                fight.getId(),
                fight.getStatus(),
                fight.getResultSummary(),
                fight.getExecutedAt()
        );
    }

    @GetMapping
    public List<Fight> findAll() {
        return fightService.findAll();
    }

    @GetMapping("/{id}")
    public Fight findById(@PathVariable Long id) {
        return fightService.findById(id);
    }

    @PutMapping("/{id}")
    public Fight update(@PathVariable Long id, @Valid @RequestBody FightUpdateDTO dto) {
        return fightService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fightService.delete(id);
    }
}
