package com.example.securityproject.controller;

import com.example.securityproject.models.Message;
import com.example.securityproject.service.ChatConsumer;
import com.example.securityproject.service.ChatProducer;
import com.example.securityproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("chat")
@RestController
public class ChatRestController {
    @Autowired
    private ChatConsumer chatConsumer;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatProducer chatProducer;

    @PostMapping("/sendMessageForAdmin")
    public HttpStatus sendMessage(@RequestParam String message, @RequestParam String receiverId) {
        return chatProducer.sendMessage(message,receiverId);
    }
    @PostMapping("/sendMessageForClient")
    public HttpStatus sendMessageClient(@RequestParam String message) {
        return chatProducer.sendMessage(message);
    }

    @GetMapping("/getMessageForAdmin")
    public List<Map<String, Message>> getMessageForAdmin() {
        return chatConsumer.consumeAllMessages();
    }

    @GetMapping("/getMessageForClient")
    public List<Map<String, Message>> getMessageForClient(@RequestParam String key) {
        return chatConsumer.consumeExactUser(key);
    }

    @GetMapping("/getSenderName")
    public String getSenderName(@RequestParam String senderId) {
        return userService.getUserName(senderId);
    }
}
