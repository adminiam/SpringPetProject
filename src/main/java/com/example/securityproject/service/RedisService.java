package com.example.securityproject.service;

import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RedisService {
    @Autowired
    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    private final ObjectMapper objectMapper;

    public RedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void saveMessage(UUID senderId, Message message) throws JsonProcessingException {
        redisTemplate.opsForList().rightPush("user:" + senderId, objectMapper.writeValueAsString(message));
        redisTemplate.expire("user:" + senderId, Duration.ofHours(1));
    }

    public List<Message> getMessages(UUID senderId) throws JsonProcessingException {
        List<String> messageJsons = redisTemplate.opsForList().range("user:" + senderId, 0 , -1);
        List<Message> messages = new ArrayList<>();

        if (messageJsons != null) {
            for (String messageJson : messageJsons) {
                Message message = objectMapper.readValue(messageJson, Message.class);
                messages.add(message);
            }
        }

        return messages;
    }
    public String showMessage(){
        try {
            List<Message> messages = getMessages(UUID.fromString("411a50e5-694f-4857-b92e-8fbc6e8237ac"));
            for (Message message : messages) {
                System.out.println(message);
            }

        }
        catch (JsonProcessingException e) {
            throw new SuppressedStackTraceException(e.getMessage());
        }
        return "/adminPanel";
    }
}
