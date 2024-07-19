package com.example.securityproject.config.dbconfig;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Argon2PasswordEncoderConfig {

    public String generateHash(char[] password) {
        String hash = null;
        Argon2 argon2 = Argon2Factory.create();
        try {
            hash = argon2.hash(20, 65536, 1, password);
            return hash;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
