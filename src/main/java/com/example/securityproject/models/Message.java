package com.example.securityproject.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Message {
    private UUID id;
    private String message;
    private UUID senderId;
    private UUID chatRoomId;
}
