package com.example.securityproject.controller;

import com.example.securityproject.dto.LoginRequest;
import com.example.securityproject.dto.RegisterRequest;
import com.example.securityproject.service.AuthenticationService;
import com.example.securityproject.service.RegistryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RegistryService registryService;

    public AuthController(AuthenticationService authenticationService, RegistryService registryService) {
        this.authenticationService = authenticationService;
        this.registryService = registryService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return authenticationService.login(request, response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return authenticationService.logout(response);
    }

    @PostMapping("/register")
    public HttpStatus register(@RequestBody RegisterRequest registerRequest) {
        return registryService.register(registerRequest.getName(), registerRequest.getPassword());
    }
}
