package com.example.securityproject.controller;

import com.example.securityproject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @PostMapping("createClient")
    public RedirectView createOrder(@RequestParam String userName, @RequestParam String password, @RequestParam String role)
    {
        return adminService.createClient(userName, password, role);
    }
    @GetMapping("updateModalClient")
    public RedirectView updateModalClient(@RequestParam String userName, RedirectAttributes redirectAttributes) {
        return adminService.updateOrderOpenModal(userName,redirectAttributes);
    }
    @PostMapping("updateClient")
    public RedirectView updateModalClient(@RequestParam String idModal, String roleModal) {
        return adminService.updateClient(idModal,roleModal);
    }
    @PostMapping("deleteClient")
    public RedirectView deleteClient(@RequestParam String userName) {
        return adminService.deleteClient(userName);
    }
    @PostMapping("deleteAdmins")
    public RedirectView deleteAdmins() {
        return adminService.deleteAdmins();
    }
}
