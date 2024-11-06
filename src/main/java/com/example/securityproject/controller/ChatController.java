package com.example.securityproject.controller;

import com.example.securityproject.service.ChatProducer;
import com.example.securityproject.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class ChatController {
    @Autowired
    private ChatProducer chatProducer;
    @Autowired
    private RedisService redisService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message, @RequestParam UUID senderId) {
        return chatProducer.sendMessage(message,senderId);
    }
    @PostMapping("/sendClient")
    public String sendMessageClient(@RequestParam String message) {
        return chatProducer.sendMessage(message);
    }

    @GetMapping("/show")
    public String show() {
        return redisService.showMessage();
    }

}
