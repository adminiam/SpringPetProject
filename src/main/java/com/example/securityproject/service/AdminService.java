package com.example.securityproject.service;


import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
}
