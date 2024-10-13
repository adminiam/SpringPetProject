package com.example.securityproject.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Message {
    private UUID id;
    private String message;
    private UUID senderId;
}
