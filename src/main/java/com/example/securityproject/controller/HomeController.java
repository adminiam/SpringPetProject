package com.example.securityproject.controller;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;
    @Autowired
    UserContext userContext;

    @GetMapping("home")
    public String getTrackingStatuses(Model model) {
        homeService.getTrackingStatuses(model);
        model.addAttribute("userId", userContext.getId());
        return "home";
    }
}
