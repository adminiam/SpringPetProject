package com.example.securityproject.controller;

import com.example.securityproject.models.Message;
import com.example.securityproject.service.ChatConsumer;
import com.example.securityproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatRestController {
    @Autowired
    private ChatConsumer chatConsumer;
    @Autowired
    UserService userService;

    @GetMapping("/getMessage")
    public List<Message> getMessage(){
        return chatConsumer.getList();
    }
    @GetMapping("/getSenderName")
    public String getSenderName(@RequestParam String senderId) {
        return userService.getUserName(senderId);
    }
}
