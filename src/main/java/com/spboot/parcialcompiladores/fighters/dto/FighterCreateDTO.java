package com.spboot.parcialcompiladores.fighters.dto;
import jakarta.validation.constraints.*;

public record FighterCreateDTO (
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 80)
    String name,

    @Min(value = 0, message = "Strength must be >=0")
    @Max(value = 100, message = "Strength must be <=100")
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
