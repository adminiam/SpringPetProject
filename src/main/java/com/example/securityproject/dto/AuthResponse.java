package com.example.securityproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String role;
    private String message;
    private String token;

    public AuthResponse(String role, String message, String token) {
        this.role = role;
        this.message = message;
        this.token = token;
    }
}
