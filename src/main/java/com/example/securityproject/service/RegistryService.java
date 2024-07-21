package com.example.securityproject.service;

import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistryService {
    @Autowired
    PasswordEncoderService encoderService;
    @Autowired
    JpaClientRepo clientRepo;

    public ResponseEntity<String> register(String name, String password) {
        if (!name.isEmpty() && !password.isEmpty()) {
            if (clientRepo.getClientByLoginName(name) != null) {
                return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
            } else {
                Client client = new Client();
                client.setIdClientUUID(UUID.randomUUID());
                client.setLoginName(name);
                client.setPassword(encoderService.generatePassword(password));
                clientRepo.save(client);
                return new ResponseEntity<>("redirect:/login", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Name and password must not be empty", HttpStatus.BAD_REQUEST);
        }
    }

}
