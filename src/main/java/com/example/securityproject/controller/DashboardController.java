package com.example.securityproject.controller;

import com.example.securityproject.entities.Order;
import com.example.securityproject.service.HomeService;
import com.example.securityproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dashboard")
public class DashboardController {
    @Autowired
    OrderService orderService;
    @Autowired
    HomeService homeService;

    @GetMapping("getTrackingNumbers")
    public List<Order> getTrackingStatuses() {
        return homeService.getTrackingStatuses();
    }

    @PostMapping("createOrder")
    public HttpStatus createOrder(@RequestParam String email,
                                  @RequestParam String orderNumber,
                                  @RequestParam String description) {
        return orderService.createOrder(email, orderNumber, description);
    }

    @PostMapping("updateOrder")
    public HttpStatus updateOrder(@RequestParam String id,
                                  @RequestParam String email,
                                  @RequestParam String orderNumber,
                                  @RequestParam String description) {
        return orderService.updateOrder(id, email, orderNumber, description);
    }


    @PostMapping("deleteOrder")
    public HttpStatus deleteOrder(@RequestBody String trackingNumber) {
        return orderService.deleteOrder(trackingNumber);
    }

    @PostMapping("deleteAllOrders")
    public HttpStatus deleteAllOrders() {
        return orderService.deleteAllOrders();
    }
}
