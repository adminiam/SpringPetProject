package com.example.securityproject.controller;

import com.example.securityproject.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistryController {
    @Autowired
    RegistryService registryService;

    @PostMapping("register")
    public HttpStatus register(@RequestParam String name, @RequestParam String password) {
        return registryService.register(name, password);
    }
}

