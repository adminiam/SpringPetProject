package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.dto.SendMessage;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatProducer {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    private UserContext userContext;

    public HttpStatus sendMessage(SendMessage request) {
        try {
            Message msg = Message.builder()
                    .id(UUID.randomUUID())
                    .message(request.getMessage())
                    .senderId(userContext.getId())
                    .receiverId(UUID.fromString(request.getReceiverId()))
                    .build();

            kafkaTemplate.send("chat_topic", request.getReceiverId(), msg);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus sendMessage(String message) {
        try {
            Message msg = Message.builder().id(UUID.randomUUID()).message(message).senderId(userContext.getId()).receiverId(UUID.fromString("49ec45e2-5026-4dcb-9423-b34bd7e9a845")).build();
            if (msg.getSenderId() != null) {
                kafkaTemplate.send("chat_topic", String.valueOf(msg.getSenderId()), msg);
                return HttpStatus.OK;
            }
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

}

