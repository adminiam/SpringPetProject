package com.example.securityproject.controller;

import com.example.securityproject.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
@Autowired
    HomeService homeService;
    @GetMapping("home")
    public String getTrackingStatuses(Model model) {
        homeService.getTrackingStatuses(model);
        return "home";
    }
    @GetMapping("adminPanel")
    public String getAdminPanel(){
        return "adminPanel";
    }
}
