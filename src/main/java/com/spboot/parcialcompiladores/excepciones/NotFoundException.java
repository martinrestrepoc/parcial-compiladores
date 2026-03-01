package com.spboot.parcialcompiladores.excepciones;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}