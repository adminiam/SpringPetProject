package com.example.securityproject.controller;

import com.example.securityproject.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    RegistryService registryService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String name, @RequestParam String password) {
        return registryService.register(name, password);
    }
}

