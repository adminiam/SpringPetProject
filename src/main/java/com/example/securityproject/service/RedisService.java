package com.example.securityproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveMessage(String messageId, String messageContent) {
        redisTemplate.opsForValue().set("chat-message:" + messageId, messageContent, 1, TimeUnit.HOURS);
    }

    public String getMessage(String messageId) {
        return redisTemplate.opsForValue().get("chat-message:" + messageId);
    }
}
