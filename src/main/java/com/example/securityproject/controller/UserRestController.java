package com.example.securityproject.controller;

import com.example.securityproject.components.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("user")
@RestController
public class UserRestController {
    @Autowired
    private UserContext userContext;

    @GetMapping("/getContext")
    public String getUserContext() {
        return userContext.getId().toString();
    }
}
