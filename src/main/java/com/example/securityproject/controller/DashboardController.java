package com.example.securityproject.controller;

import com.example.securityproject.dto.OrderCreate;
import com.example.securityproject.dto.OrderDelete;
import com.example.securityproject.dto.OrderUpdate;
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
    public HttpStatus createOrder(OrderCreate order) {
        return orderService.createOrder(order);
    }

    @PostMapping("updateOrder")
    public HttpStatus updateOrder(@RequestBody OrderUpdate order) {
        return orderService.updateOrder(order);
    }


    @PostMapping("deleteOrder")
    public HttpStatus deleteOrder(@RequestBody OrderDelete trackingNumber) {
        return orderService.deleteOrder(trackingNumber.getTrackingNumber());
    }

    @PostMapping("deleteAllOrders")
    public HttpStatus deleteAllOrders() {
        return orderService.deleteAllOrders();
    }
}
