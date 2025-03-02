package com.example.securityproject.controller;

import com.example.securityproject.entities.Client;
import com.example.securityproject.annotations.Auditable;
import com.example.securityproject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("adminPanel")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("")
    public List<Client> getClientsList() {
        return adminService.getClientsList();
    }

    @Auditable(action = "Admin created new client")
    @PostMapping("createClient")
    public HttpStatus createOrder(@RequestParam String userName, @RequestParam String password, @RequestParam String role) {
        return adminService.createClient(userName, password, role);
    }

    @Auditable(action = "Admin updated client")
    @PostMapping("updateClient")
    public HttpStatus updateClient(@RequestParam String idModal, String roleModal) {
        return adminService.updateClient(idModal, roleModal);
    }

    @Auditable(action = "Admin deleted client")
    @PostMapping("deleteClient")
    public HttpStatus deleteClient(@RequestParam String userName) {
        return adminService.deleteClient(userName);
    }

    @PostMapping("deleteAllClients")
    public HttpStatus deleteAdmins() {
        return adminService.deleteAdmins();
    }
}
