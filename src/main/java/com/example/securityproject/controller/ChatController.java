package com.example.securityproject.controller;

import com.example.securityproject.service.ChatProducer;
import com.example.securityproject.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
    @Autowired
    private ChatProducer chatProducer;
    @Autowired
    private RedisService redisService;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        return chatProducer.sendMessage(message);
    }

    @GetMapping("/show")
    public String show() {
        return redisService.showMessage();
    }

}
