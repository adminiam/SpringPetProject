package com.example.securityproject.controller;

import com.example.securityproject.dto.LoginRequest;
import com.example.securityproject.dto.RegisterRequest;
import com.example.securityproject.service.AuthenticationService;
import com.example.securityproject.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    RegistryService registryService;

    @PostMapping("register")
    public HttpStatus register(@RequestBody RegisterRequest registerRequest) {
        return registryService.register(registerRequest.getName(), registerRequest.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}