package com.example.securityproject.service;

import com.example.securityproject.models.Message;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

@Service
public class ChatConsumer {
    private final Gson gson = new Gson();

    @KafkaListener(topics = "chat_topic", groupId = "myGroup")
    public void listen(String message) {
        Message parsedMessage = gson.fromJson(message, Message.class);
        System.out.println(parsedMessage.getMessage());
        System.out.println(parsedMessage.getId());
    }
}
