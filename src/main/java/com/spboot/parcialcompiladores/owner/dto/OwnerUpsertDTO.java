package com.spboot.parcialcompiladores.owner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OwnerUpsertDTO(
        @NotBlank @Size(min = 2, max = 100) String name,
        @NotBlank @Size(min = 2, max = 100) String alias,
        @Size(max = 2000) String bio,
        @Size(max = 4000) String backstory
) {}