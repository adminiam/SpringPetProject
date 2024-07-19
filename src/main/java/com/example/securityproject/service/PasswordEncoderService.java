package com.example.securityproject.service;

import com.example.securityproject.config.dbconfig.Argon2PasswordEncoderConfig;
import com.example.securityproject.repository.JpaClientRepo;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PasswordEncoderService {
    @Autowired
    Argon2PasswordEncoderConfig passwordEncoderConfig;
    @Autowired
    JpaClientRepo clientRepo;

    public String generatePassword(String password) {
        return passwordEncoderConfig.generateHash(password.toCharArray());
    }

    public boolean passwordVerify(String username, String rawPassword) {
        try {
            if (username != null && rawPassword != null || !Objects.equals(username, "") && !Objects.equals(rawPassword, "")) {
                Argon2 argon2 = Argon2Factory.create();
                String storedHash = clientRepo.getClientPasswordByLoginName(username);
                if (rawPassword != null) {
                    return argon2.verify(storedHash, rawPassword.toCharArray());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
