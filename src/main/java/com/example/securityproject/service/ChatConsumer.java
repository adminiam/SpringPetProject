package com.example.securityproject.service;

import com.example.securityproject.models.Message;
import com.google.gson.Gson;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatConsumer {
    private final Gson gson = new Gson();
    @Getter
    private final Map<String,Message> list = new HashMap<>();
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @KafkaListener(topics = "chat_topic", groupId = "myGroup")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String message = record.value();
        String id = redisTemplate.opsForValue().get("user_session:" + key);
        if (id!=null) {
            Message parsedMessage = gson.fromJson(message, Message.class);
            list.put(key,parsedMessage);
            System.out.println(parsedMessage.getMessage());
            System.out.println(parsedMessage.getSenderId());
            System.out.println(parsedMessage.getReceiverId());
        }
    }
}
