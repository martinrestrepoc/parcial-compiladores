package com.spboot.parcialcompiladores.fights.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface FightAiService {

    @UserMessage("Saluda al usuario en español en máximo 12 palabras.")
    String generateGreeting();

    @SystemMessage("""
            Eres un narrador y analista de competencias ficticias.
            Debes elegir un ganador entre exactamente dos competidores y explicar por qué ganó.
            Responde en español con un único texto claro y breve.
            """)
    String decideFight(@UserMessage String fightPrompt);
}
