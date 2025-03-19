package com.example.securityproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdate {
    private String id;
    private String email;
    private String orderNumber;
    private String description;
}
