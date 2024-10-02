package com.example.securityproject.service;

import com.example.securityproject.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatProducer {
        @Autowired
        private KafkaTemplate<String, Message> kafkaTemplate;
        public String sendMessage() {
            Message msg = new Message();
            msg.setId(UUID.randomUUID());
            msg.setMessage("test");
            msg.setChatRoomId(UUID.randomUUID());
            msg.setSenderId(UUID.randomUUID());
            kafkaTemplate.send("chat_topic", msg);
            return "home";
        }
    }

