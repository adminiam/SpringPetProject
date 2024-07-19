package com.example.securityproject.service;

import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistryService {
    @Autowired
    PasswordEncoderService encoderService;
    @Autowired
    JpaClientRepo clientRepo;

    public void register(String name, String password) {
        Client client = new Client();
        client.setIdClientUUID(UUID.randomUUID());
        client.setLoginName(name);
        client.setPassword(encoderService.generatePassword(password));
        clientRepo.save(client);
    }
}
