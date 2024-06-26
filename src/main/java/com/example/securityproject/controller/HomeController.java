package com.example.securityproject.controller;

import com.example.securityproject.repository.JpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @Autowired
    JpaRepo jpaRepo;

    @GetMapping("/home")
    public String getTrackingStatuses(Model model) {
        model.addAttribute("trackingNumbers", jpaRepo.findAllOrders());
        return "home";
    }
}
