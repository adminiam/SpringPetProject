package com.example.securityproject.controller;

import com.example.securityproject.entities.Order;
import com.example.securityproject.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("home")
@RestController
public class HomeController {
    @Autowired
    HomeService homeService;

    @GetMapping("getTrackingNumbers")
    public List<Order> getTrackingStatuses() {
        return homeService.getTrackingStatuses();
    }
}
