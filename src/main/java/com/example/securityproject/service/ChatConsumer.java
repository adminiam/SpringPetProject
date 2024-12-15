package com.example.securityproject.service;

import com.example.securityproject.models.Message;

import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatConsumer {
    private final Gson gson = new Gson();
    @Getter
    List<Message> list = new ArrayList<>();

    @KafkaListener(topics = "chat_topic", groupId = "myGroup")
    public void listen(String message) {
        Message parsedMessage = gson.fromJson(message, Message.class);
        list.add(parsedMessage);
        System.out.println(parsedMessage.getMessage());
        System.out.println(parsedMessage.getSenderId());
        System.out.println(parsedMessage.getReceiverId());
    }
}
