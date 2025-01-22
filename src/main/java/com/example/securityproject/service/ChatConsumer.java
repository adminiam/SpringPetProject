package com.example.securityproject.service;

import com.example.securityproject.models.Message;
import com.google.gson.Gson;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatConsumer {
    private final Gson gson = new Gson();
    @Getter
    private final List<Map<String, Message>> list = new ArrayList<>();

    @KafkaListener(topics = "chat_topic", groupId = "myGroup")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String message = record.value();
        Map<String, Message> map = new HashMap<>();
        Message parsedMessage = gson.fromJson(message, Message.class);
        map.put(key, parsedMessage);
        list.add(map);
        System.out.println(parsedMessage.getMessage());
        System.out.println(parsedMessage.getSenderId());
        System.out.println(parsedMessage.getReceiverId());
    }

    public List<Map<String,Message>>consume(String key) {
        return list.stream()
                .filter(map -> map.containsKey(key))
                .collect(Collectors.toList());
    }
}
