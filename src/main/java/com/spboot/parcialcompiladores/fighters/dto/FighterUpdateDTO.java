package com.spboot.parcialcompiladores.fighters.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FighterUpdateDTO (
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 80)
        String name,

        @Min(0)
        @Max(100)
        int strength,

        @Min(0)
        @Max(100)
        int speed,

        @Min(0)
        @Max(100)
        int stamina,

        @NotBlank(message = "Special skill is required")
        String specialSkill,

        @Size(max = 2000)
        String bio,

        @Size(max = 4000)
        String backstory
){}
