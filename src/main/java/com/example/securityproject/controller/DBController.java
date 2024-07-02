package com.example.securityproject.controller;

import com.example.securityproject.entities.Order;
import com.example.securityproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("database")
public class DBController {
    @Autowired
    OrderService orderService;
    @PostMapping("create")
    public RedirectView createOrder(@RequestParam String email, @RequestParam String orderNumber, @RequestParam String description) {
        return orderService.createOrder(email,orderNumber,description);
    }
    @PostMapping("update")
    public ResponseEntity<Order> updateOrder(@RequestParam Long id,@RequestParam String email, @RequestParam String orderNumber, @RequestParam String description) {
        return orderService.updateOrder(id,email,orderNumber,description);
    }
    @PostMapping("delete")
    public RedirectView deleteOrder(@RequestParam Long trackingNumber) {
         return orderService.deleteOrder(trackingNumber);
    }
}
