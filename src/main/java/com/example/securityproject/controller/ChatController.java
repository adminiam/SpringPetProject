package com.example.securityproject.controller;

import com.example.securityproject.service.ChatProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @Autowired
    private ChatProducer chatProducer;

    @GetMapping("/send")
    public String sendMessage(){
        return chatProducer.sendMessage();
    }

}
