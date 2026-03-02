package com.spboot.parcialcompiladores.fights.dto;

import jakarta.validation.constraints.Size;

public record FightExecuteDTO(
        @Size(max = 1000, message = "Context must be <= 1000 characters")
        String context
) {
}
