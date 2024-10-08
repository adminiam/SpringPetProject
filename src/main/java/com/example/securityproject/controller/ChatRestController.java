package com.example.securityproject.controller;

import com.example.securityproject.models.Message;
import com.example.securityproject.service.ChatConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatRestController {
    @Autowired
    private ChatConsumer chatConsumer;
    @GetMapping("/getMessage")
    public List<Message> getMessage(){
        return chatConsumer.getList();
    }
}
