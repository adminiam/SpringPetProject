package com.example.securityproject.service;

import com.example.securityproject.entities.Client;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class AdminService {
    @Autowired
    JpaClientRepo jpaClientRepo;
    @Autowired
    PasswordEncoderService encoderService;

    public List<Client> getClientsList() {
        try {
            return jpaClientRepo.findAll();
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public HttpStatus deleteClient(String userName) {
        try {
            Client client = jpaClientRepo.getClientByLoginName(userName);
            if (client != null) {
                jpaClientRepo.delete(client);
                return HttpStatus.OK;
            }

        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus createClient(String userName, String password, String role) {
        try {
            if (jpaClientRepo.getClientByLoginName(userName) == null) {
                Client client = new Client();
                client.setIdClientUUID(UUID.randomUUID());
                client.setLoginName(userName);
                client.setPassword(encoderService.generatePassword(password));
                client.setRole(role);
                jpaClientRepo.save(client);
                return HttpStatus.OK;
            } else {
                throw new SuppressedStackTraceException("Client already exists");
            }
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public HttpStatus updateClient(String userName, String roleModal) {
        try {
            Client client = jpaClientRepo.getClientByLoginName(userName);
            Client newClient = new Client();
            newClient.setIdClientUUID(client.getIdClientUUID());
            newClient.setLoginName(client.getLoginName());
            newClient.setPassword(client.getPassword());
            newClient.setRole(roleModal);
            jpaClientRepo.save(newClient);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }

    }

    public HttpStatus deleteAdmins(){
        try {
            jpaClientRepo.deleteAllAdmins();
            return HttpStatus.OK;
        }catch (DataAccessException e){
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }
}
