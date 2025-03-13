package com.example.securityproject.service;

import com.example.securityproject.entities.Client;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistryService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JpaClientRepo clientRepo;

    public HttpStatus register(String name, String password) {
        try {
            if (!name.isEmpty() && !password.isEmpty()) {
                if (clientRepo.getClientByLoginName(name) != null || !name.matches("^[A-Za-z0-9]+$")) {
                    return HttpStatus.BAD_REQUEST;
                }
                else if (password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*")) {
                    return HttpStatus.BAD_REQUEST;
                }
                else {
                    Client client = new Client();
                    client.setIdClientUUID(UUID.randomUUID());
                    client.setLoginName(name);
                    client.setPassword(passwordEncoder.encode(password));
                    client.setRole("USER");
                    clientRepo.save(client);
                    return HttpStatus.OK;
                }
            } else {
                return HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }
}