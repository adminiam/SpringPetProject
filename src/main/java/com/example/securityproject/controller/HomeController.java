package com.example.securityproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

  @GetMapping("/home")
  public String getTrackingStatuses(Model model) {
    List<String> trackingNumbers = Arrays.asList("In Transit", "Delivered", "Awaiting Pickup");
    model.addAttribute("trackingNumbers", trackingNumbers);
    return "home";
  }
}
