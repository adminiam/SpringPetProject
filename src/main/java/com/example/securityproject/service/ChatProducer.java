package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatProducer {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserContext userContext;

    public ResponseEntity<?> sendMessage(String message, String receiverId) {
        try {
            Message msg = Message.builder()
                    .id(UUID.randomUUID())
                    .message(message)
                    .senderId(userContext.getId())
                    .receiverId(UUID.fromString(receiverId))
                    .build();

            kafkaTemplate.send("chat_topic", receiverId, msg);
            redisService.saveMessage(msg.getSenderId(), msg);

            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    public String sendMessage(String message) {
        try {
            Message msg = Message.builder().id(UUID.randomUUID()).message(message).senderId(userContext.getId()).receiverId(UUID.fromString("49ec45e2-5026-4dcb-9423-b34bd7e9a845")).build();
            kafkaTemplate.send("chat_topic", String.valueOf(msg.getSenderId()),msg);
            redisService.saveMessage(msg.getSenderId(), msg);
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return "home";
    }

}

