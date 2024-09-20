package com.example.securityproject.controller;

import com.example.securityproject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("adminPanel")
    public String getAdminPanel(Model model) {
        adminService.getClientsList(model);
        return "adminPanel";
    }
}
