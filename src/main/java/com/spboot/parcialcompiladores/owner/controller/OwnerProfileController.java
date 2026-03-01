package com.spboot.parcialcompiladores.owner.controller;

import com.spboot.parcialcompiladores.owner.dto.OwnerUpsertDTO;
import com.spboot.parcialcompiladores.owner.model.OwnerProfile;
import com.spboot.parcialcompiladores.owner.service.OwnerProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simon")
public class OwnerProfileController {

    private final OwnerProfileService service;

    public OwnerProfileController(OwnerProfileService service) {
        this.service = service;
    }

    @GetMapping
    public OwnerProfile get() {
        return service.get();
    }

    @PutMapping
    public OwnerProfile upsert(@Valid @RequestBody OwnerUpsertDTO dto) {
        return service.upsert(dto);
    }
}
