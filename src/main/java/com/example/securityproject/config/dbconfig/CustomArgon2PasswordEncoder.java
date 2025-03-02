package com.example.securityproject.config.dbconfig;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomArgon2PasswordEncoder implements PasswordEncoder {

    private final Argon2 argon2;

    public CustomArgon2PasswordEncoder() {
        this.argon2 = Argon2Factory.create();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return argon2.hash(20, 65536, 1, rawPassword.toString().toCharArray());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return argon2.verify(encodedPassword, rawPassword.toString().toCharArray());
    }
}