package com.example.securityproject.controller;

import com.example.securityproject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("adminPanel")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("")
    public String getAdminPanel(Model model) {
        adminService.getClientsList(model);
        return "adminPanel";
    }
    @PostMapping("deleteClient")
    public RedirectView deleteOrder(@RequestParam String userName) {
        return adminService.deleteOrder(userName);
    }
}