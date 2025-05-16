package com.example.securityproject.controller;

import com.example.securityproject.dto.GetMessageForClient;
import com.example.securityproject.dto.SendMessage;
import com.example.securityproject.dto.SendMessageClient;
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
    public HttpStatus sendMessage(@RequestBody SendMessage sendMessage) {
        return chatProducer.sendMessage(sendMessage);
    }
    @PostMapping("/sendMessageForClient")
    public HttpStatus sendMessageClient(@RequestBody SendMessageClient sendMessage) {
        return chatProducer.sendMessage(sendMessage.getMessage());
    }

    @GetMapping("/getMessageForAdmin")
    public List<Map<String, Message>> getMessageForAdmin() {
        return chatConsumer.consumeAllMessages();
    }

    @GetMapping("/getMessageForClient")
    public List<Map<String, Message>> getMessageForClient(@RequestParam GetMessageForClient key) {
        return chatConsumer.consumeExactUser(key.getKey());
    }

    @GetMapping("/getSenderName")
    public String getSenderName(@RequestParam String senderId) {
        return userService.getUserName(senderId);
    }
}
