package com.spboot.parcialcompiladores.fights.dto;

import com.spboot.parcialcompiladores.fights.model.FightStatus;

import java.time.Instant;

public record FightExecuteResponseDTO(
        Long id,
        FightStatus status,
        String resultSummary,
        Instant executedAt
) {
}
