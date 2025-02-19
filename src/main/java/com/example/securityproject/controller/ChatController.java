package com.example.securityproject.controller;

import com.example.securityproject.service.ChatProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
    @Autowired
    private ChatProducer chatProducer;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam String message, @RequestParam String receiverId) {
        return chatProducer.sendMessage(message,receiverId);
    }
    @PostMapping("/sendClient")
    public String sendMessageClient(@RequestParam String message) {
        return chatProducer.sendMessage(message);
    }

}
