package com.example.securityproject.service;

import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    JpaClientRepo jpaClientRepo;

    public String getUserName(String username) {
        Client client = new Client();
        client.setIdClientUUID(UUID.fromString(username));
        return jpaClientRepo.getClientByIdClient(client.getIdClient()).getLoginName();
    }
}
