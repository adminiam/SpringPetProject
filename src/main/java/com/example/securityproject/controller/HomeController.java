package com.example.securityproject.controller;

import com.example.securityproject.repository.JpaOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @Autowired
    JpaOrderRepo jpaOrderRepo;

    @GetMapping("/home")
    public String getTrackingStatuses(Model model) {
        model.addAttribute("trackingNumbers", jpaOrderRepo.findAll());
        return "home";
    }
}
