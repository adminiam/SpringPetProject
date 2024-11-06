package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String sendMessage(String message, UUID receiverId) {
        //todo
        try {
            Message msg = Message.builder().id(UUID.randomUUID()).message(message).senderId(userContext.getId()).build();
            kafkaTemplate.send("chat_topic", msg);
            redisService.saveMessage(msg.getSenderId(), msg);
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return "home";
    }

    public String sendMessage(String message) {
        try {
            Message msg = Message.builder().id(UUID.randomUUID()).message(message).senderId(userContext.getId()).build();
            kafkaTemplate.send("chat_topic", msg);
            redisService.saveMessage(msg.getSenderId(), msg);
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return "home";
    }

}

