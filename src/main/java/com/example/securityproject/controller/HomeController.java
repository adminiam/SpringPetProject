package com.example.securityproject.controller;

import com.example.securityproject.repository.JpaOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
    @Autowired
    JpaOrderRepo jpaOrderRepo;

    @GetMapping("/home")
    public String getTrackingStatuses(@RequestParam Long id, Model model) {
        model.addAttribute("trackingNumbers", jpaOrderRepo.findAllByClientId(id));
        return "home";
    }
}
