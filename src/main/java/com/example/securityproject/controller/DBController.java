package com.example.securityproject.controller;

import com.example.securityproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("database")
public class DBController {
    @Autowired
    OrderService orderService;

    @PostMapping("create")
    public RedirectView createOrder(@RequestParam String email,
                                    @RequestParam String orderNumber,
                                    @RequestParam String description) {
        return orderService.createOrder(email, orderNumber, description);
    }

    @PostMapping("update")
    public RedirectView updateOrder(@RequestParam String id,
                                    @RequestParam String email,
                                    @RequestParam String orderNumber,
                                    @RequestParam String description) {
        return orderService.updateOrder(id, email, orderNumber, description);
    }

    @GetMapping("updateModal")
    public RedirectView updateOrder(@RequestParam String id,
                                    RedirectAttributes redirectAttributes) {
        return orderService.updateOrderOpenModal(id, redirectAttributes);
    }

    @PostMapping("delete")
    public RedirectView deleteOrder(@RequestParam String trackingNumber) {
        return orderService.deleteOrder(trackingNumber);
    }

    @PostMapping("deleteAll")
    public RedirectView deleteAllOrders() {
        return orderService.deleteAllOrders();
    }
}
