package com.spboot.parcialcompiladores.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {

        System.out.println("DATA LOADER EJECUTANDO");

        if (!userRepository.existsByUsername("simon maligno")) {

            User user = new User(
                    "simon maligno",
                    encoder.encode("Simon123")
            );

            userRepository.save(user);

            System.out.println("Usuario creado con password encriptado");
        }
    }
}
