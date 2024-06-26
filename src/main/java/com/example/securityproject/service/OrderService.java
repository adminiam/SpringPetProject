package com.example.securityproject.service;

import com.example.securityproject.entities.Order;
import com.example.securityproject.repository.JpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    JpaRepo jpaRepo;

    public ResponseEntity<Order> createOrder(String email, String orderNumber, String description) {
        try {
            Order order = new Order();
            order.setEmail(email);
            order.setOrderNumber(orderNumber);
            order.setDescription(description);
            jpaRepo.save(order);
            return ResponseEntity.ok(order);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<Order> updateOrder(Long id, String email, String orderNumber, String description) {
        try {
            Optional<Order> existingOrder = jpaRepo.findById(id);
            if (existingOrder.isPresent()) {
                Order updatedOrder = existingOrder.get();
                if (!email.isEmpty()) updatedOrder.setEmail(email);
                else updatedOrder.setEmail(existingOrder.get().getEmail());
                if (!orderNumber.isEmpty()) updatedOrder.setOrderNumber(orderNumber);
                else updatedOrder.setOrderNumber(existingOrder.get().getOrderNumber());
                if (!description.isEmpty()) updatedOrder.setDescription(description);
                else updatedOrder.setDescription(existingOrder.get().getDescription());
                jpaRepo.save(updatedOrder);
                return ResponseEntity.ok(updatedOrder);
            }
            else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
