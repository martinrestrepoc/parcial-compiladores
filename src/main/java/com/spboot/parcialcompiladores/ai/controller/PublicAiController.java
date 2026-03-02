package com.spboot.parcialcompiladores.ai.controller;

import com.spboot.parcialcompiladores.fights.service.FightAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public/ai")
public class PublicAiController {

    private final FightAiService fightAiService;

    public PublicAiController(FightAiService fightAiService) {
        this.fightAiService = fightAiService;
    }

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        try {
            String greeting = fightAiService.generateGreeting();
            return ResponseEntity.ok(Map.of("ok", "true", "message", greeting));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of(
                    "ok", "false",
                    "error", ex.getClass().getSimpleName(),
                    "message", String.valueOf(ex.getMessage())
            ));
        }
    }
}
