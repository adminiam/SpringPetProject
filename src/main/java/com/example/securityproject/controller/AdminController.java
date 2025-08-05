package com.example.securityproject.controller;

import com.example.securityproject.dto.ClientCreate;
import com.example.securityproject.dto.ClientDelete;
import com.example.securityproject.dto.ClientUpdate;
import com.example.securityproject.dto.PaginatedResponse;
import com.example.securityproject.entities.Client;
import com.example.securityproject.annotations.Auditable;
import com.example.securityproject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("adminPanel")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("")
    public PaginatedResponse<Client> getClientsList(@RequestParam int page, @RequestParam int pageSize) {
        return adminService.getClientsList(page,pageSize);
    }

    @Auditable(action = "Admin created new client")
    @PostMapping("createClient")
    public HttpStatus createClient(@RequestBody ClientCreate client) {
        return adminService.createClient(client);
    }

    @Auditable(action = "Admin updated client")
    @PostMapping("updateClient")
    public HttpStatus updateClient(@RequestBody ClientUpdate client) {
        return adminService.updateClient(client);
    }

    @Auditable(action = "Admin deleted client")
    @PostMapping("deleteClient")
    public HttpStatus deleteClient(@RequestBody ClientDelete client) {
        return adminService.deleteClient(client.getUserName());
    }

    @PostMapping("deleteAllClients")
    public HttpStatus deleteAdmins() {
        return adminService.deleteAdmins();
    }
}
