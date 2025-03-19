package com.example.securityproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreate {
    private String userName;
    private String password;
    private String role;
}
