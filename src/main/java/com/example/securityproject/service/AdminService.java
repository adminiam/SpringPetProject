package com.example.securityproject.service;

import com.example.securityproject.dto.ClientCreate;
import com.example.securityproject.dto.ClientUpdate;
import com.example.securityproject.entities.Client;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class AdminService {
    @Autowired
    private JpaClientRepo jpaClientRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Client> getClientsList() {
        try {
            return jpaClientRepo.findAll();
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public HttpStatus deleteClient(String username) {
        try {
            Client client = jpaClientRepo.getClientByLoginName(username);
            if (client != null && !client.getRole().equals("OWNER")) {
                jpaClientRepo.delete(client);
                return HttpStatus.OK;
            }

        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus createClient(ClientCreate request) {
        try {
            if (jpaClientRepo.getClientByLoginName(request.getUserName()) == null) {
                Client client = new Client();
                client.setIdClientUUID(UUID.randomUUID());
                client.setLoginName(request.getUserName());
                client.setPassword(passwordEncoder.encode(request.getPassword()));
                client.setRole(request.getRole());
                jpaClientRepo.save(client);
                return HttpStatus.OK;
            } else {
                throw new SuppressedStackTraceException("Client already exists");
            }
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public HttpStatus updateClient(ClientUpdate request) {
        try {
            Client client = jpaClientRepo.getClientByLoginName(request.getUserName());
            if (client != null && !client.getRole().equals("OWNER")) {
                Client newClient = new Client();
                newClient.setIdClientUUID(client.getIdClientUUID());
                newClient.setLoginName(client.getLoginName());
                newClient.setPassword(client.getPassword());
                newClient.setRole(request.getRole());
                jpaClientRepo.save(newClient);
            }
            return HttpStatus.OK;

        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public HttpStatus deleteAdmins() {
        try {
            jpaClientRepo.deleteAllAdmins();
            return HttpStatus.OK;
        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }
}