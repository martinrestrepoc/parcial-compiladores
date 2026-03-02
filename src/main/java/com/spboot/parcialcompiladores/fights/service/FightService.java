package com.spboot.parcialcompiladores.fights.service;

import com.spboot.parcialcompiladores.excepciones.BadRequestException;
import com.spboot.parcialcompiladores.excepciones.NotFoundException;
import com.spboot.parcialcompiladores.fighters.model.Fighter;
import com.spboot.parcialcompiladores.fighters.repository.FighterRepository;
import com.spboot.parcialcompiladores.fights.dto.FightCreateDTO;
import com.spboot.parcialcompiladores.fights.dto.FightExecuteDTO;
import com.spboot.parcialcompiladores.fights.dto.FightUpdateDTO;
import com.spboot.parcialcompiladores.fights.model.Fight;
import com.spboot.parcialcompiladores.fights.model.FightStatus;
import com.spboot.parcialcompiladores.fights.repository.FightRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FightService {

    private final FightRepository fightRepository;
    private final FighterRepository fighterRepository;
    private final FightAiService fightAiService;

    public FightService(FightRepository fightRepository,
                        FighterRepository fighterRepository,
                        FightAiService fightAiService) {
        this.fightRepository = fightRepository;
        this.fighterRepository = fighterRepository;
        this.fightAiService = fightAiService;
    }

    public Fight create(FightCreateDTO dto) {
        validateDifferentFighters(dto.fighter1Id(), dto.fighter2Id());

        Fighter fighter1 = findFighterOrFail(dto.fighter1Id());
        Fighter fighter2 = findFighterOrFail(dto.fighter2Id());

        Fight fight = new Fight();
        fight.setFighter1(fighter1);
        fight.setFighter2(fighter2);
        fight.setStatus(FightStatus.PENDING);
        fight.setCreatedAt(Instant.now());
        fight.setResultSummary(dto.resultSummary());

        return fightRepository.save(fight);
    }

    public List<Fight> findAll() {
        return fightRepository.findAll();
    }

    public Fight findById(Long id) {
        return fightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fight with id " + id + " not found"));
    }

    public Fight update(Long id, FightUpdateDTO dto) {
        Fight fight = findById(id);

        if (fight.getStatus() == FightStatus.EXECUTED) {
            throw new BadRequestException("Executed fights cannot be updated");
        }

        validateDifferentFighters(dto.fighter1Id(), dto.fighter2Id());

        Fighter fighter1 = findFighterOrFail(dto.fighter1Id());
        Fighter fighter2 = findFighterOrFail(dto.fighter2Id());

        fight.setFighter1(fighter1);
        fight.setFighter2(fighter2);
        fight.setResultSummary(dto.resultSummary());

        return fightRepository.save(fight);
    }

    public Fight execute(Long id, FightExecuteDTO dto) {
        Fight fight = findById(id);

        if (fight.getStatus() == FightStatus.EXECUTED) {
            throw new BadRequestException("Fight is already executed");
        }

        String aiResponse = safeAiResponse(fight, dto.context());
        String summary = aiResponse == null ? "" : aiResponse.trim();
        if (summary.length() > 2000) {
            summary = summary.substring(0, 2000);
        }

        fight.setWinner(null);
        fight.setResultSummary(summary);

        fight.setStatus(FightStatus.EXECUTED);
        fight.setExecutedAt(Instant.now());

        return fightRepository.save(fight);
    }

    public void delete(Long id) {
        Fight fight = findById(id);
        fightRepository.delete(fight);
    }

    private Fighter findFighterOrFail(Long fighterId) {
        return fighterRepository.findById(fighterId)
                .orElseThrow(() -> new NotFoundException("Fighter with id " + fighterId + " not found"));
    }

    private void validateDifferentFighters(Long fighter1Id, Long fighter2Id) {
        if (fighter1Id.equals(fighter2Id)) {
            throw new BadRequestException("A fight requires two different fighters");
        }
    }

    private String buildFightPrompt(Fight fight, String context) {
        Fighter f1 = fight.getFighter1();
        Fighter f2 = fight.getFighter2();
        String extraContext = context == null ? "" : context;

        return """
                Debes simular una competencia ficticia entre estos dos competidores y elegir un ganador.
                El resultado no debe ser completamente determinístico por estadísticas; agrega un poco de imprevisibilidad.
                Debe ser coherente con las habilidades y el historial.

                Competidor 1:
                - id: %d
                - nombre: %s
                - fuerza: %d
                - velocidad: %d
                - resistencia: %d
                - habilidad especial: %s
                - bio: %s
                - historia: %s

                Competidor 2:
                - id: %d
                - nombre: %s
                - fuerza: %d
                - velocidad: %d
                - resistencia: %d
                - habilidad especial: %s
                - bio: %s
                - historia: %s

                Contexto adicional:
                %s

                Recuerda:
                - Debes decir claramente quien gano (por nombre).
                - Explica brevemente por que gano en tono narrativo deportivo.
                """.formatted(
                f1.getId(), safe(f1.getName()), f1.getStrength(), f1.getSpeed(), f1.getStamina(), safe(f1.getSpecialSkill()), safe(f1.getBio()), safe(f1.getBackstory()),
                f2.getId(), safe(f2.getName()), f2.getStrength(), f2.getSpeed(), f2.getStamina(), safe(f2.getSpecialSkill()), safe(f2.getBio()), safe(f2.getBackstory()),
                safe(extraContext)
        );
    }

    private String safeAiResponse(Fight fight, String context) {
        try {
            String prompt = buildFightPrompt(fight, context);
            return fightAiService.decideFight(prompt);
        } catch (Exception ex) {
            String message = ex.getMessage() == null ? "sin detalle" : ex.getMessage();
            return "No fue posible obtener respuesta de IA para esta pelea. Error: " + message;
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
