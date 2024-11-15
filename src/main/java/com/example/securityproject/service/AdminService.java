package com.example.securityproject.service;


import com.example.securityproject.entities.Client;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;


@Service
public class AdminService {
    @Autowired
    JpaClientRepo jpaClientRepo;
    @Autowired
    PasswordEncoderService encoderService;

    public void getClientsList(Model model) {
        try {
            model.addAttribute("clients", jpaClientRepo.findAll());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public RedirectView deleteClient(String userName) {
        try {
            Client client = jpaClientRepo.getClientByLoginName(userName);
            if (client != null) {
                jpaClientRepo.delete(client);
                return new RedirectView("/adminPanel", true);
            }

        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return new RedirectView("/error", true);
    }

    public RedirectView createClient(String userName, String password, String role) {
        try {
            if (jpaClientRepo.getClientByLoginName(userName) == null) {
                Client client = new Client();
                client.setIdClientUUID(UUID.randomUUID());
                client.setLoginName(userName);
                client.setPassword(encoderService.generatePassword(password));
                client.setRole(role);
                jpaClientRepo.save(client);
                return new RedirectView("/adminPanel", true);
            } else {
                throw new RuntimeException();
            }
        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public RedirectView updateClient(String userName, String roleModal) {
        try {
            Client client = jpaClientRepo.getClientByLoginName(userName);
            Client newClient = new Client();
            newClient.setIdClientUUID(client.getIdClientUUID());
            newClient.setLoginName(client.getLoginName());
            newClient.setPassword(client.getPassword());
            newClient.setRole(roleModal);
            jpaClientRepo.save(newClient);
            return new RedirectView("/adminPanel", true);
        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }

    }
    public RedirectView updateOrderOpenModal(String userName, RedirectAttributes redirectAttributes) {
        try {
            Client client = jpaClientRepo.getClientByLoginName(userName);
            if (client != null) {
                redirectAttributes.addFlashAttribute("idModal",userName);
                redirectAttributes.addFlashAttribute("roleModal", client.getRole());
            }
            return new RedirectView("/adminPanel?modalOpen=true", false);
        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }
    public RedirectView deleteAdmins(){
        jpaClientRepo.deleteAllAdmins();
        return new RedirectView("/adminPanel", false);
    }
}
