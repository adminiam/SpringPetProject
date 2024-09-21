package com.example.securityproject.service;


import com.example.securityproject.entities.Client;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;


@Service
public class AdminService {
    @Autowired
    JpaClientRepo jpaClientRepo;

    public void getClientsList(Model model) {
        try {
            model.addAttribute("clients", jpaClientRepo.findAll());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }
    public RedirectView deleteOrder(String userName) {
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
}
