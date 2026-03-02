package com.spboot.parcialcompiladores.excepciones;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
