package com.spboot.parcialcompiladores.fights.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FightUpdateDTO(
        @NotNull(message = "fighter1Id is required")
        @Positive(message = "fighter1Id must be positive")
        Long fighter1Id,

        @NotNull(message = "fighter2Id is required")
        @Positive(message = "fighter2Id must be positive")
        Long fighter2Id,

        @Size(max = 2000, message = "Result summary must be <= 2000 characters")
        String resultSummary
) {
}
