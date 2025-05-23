package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.dto.OrderCreate;
import com.example.securityproject.dto.OrderUpdate;
import com.example.securityproject.entities.Order;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaOrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    JpaOrderRepo jpaOrderRepo;
    @Autowired
    UserContext userContext;
    @Autowired
    UUIDService uuidService;

    public HttpStatus createOrder(OrderCreate request) {
        try {
            Order order = new Order();
            order.setIdOrder(UUID.randomUUID().toString());
            order.setEmail(request.getEmail());
            order.setOrderNumber(request.getOrderNumber());
            order.setDescription(request.getDescription());
            order.setClientId(uuidService.uuidToBytes(userContext.getId()));
            jpaOrderRepo.save(order);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public HttpStatus updateOrder(OrderUpdate request) {
        try {
            String id = request.getId();
            String email = request.getEmail();
            String orderNumber = request.getOrderNumber();
            String description = request.getDescription();
            if (email.isEmpty() && orderNumber.isEmpty() && description.isEmpty()) {
                return HttpStatus.BAD_REQUEST;
            } else {
                Optional<Order> existingOrder = jpaOrderRepo.findByIdOrder(id);
                if (existingOrder.isPresent()) {
                    Order updatedOrder = existingOrder.get();
                    if (!email.isEmpty()) updatedOrder.setEmail(email);
                    else updatedOrder.setEmail(existingOrder.get().getEmail());
                    if (!orderNumber.isEmpty()) updatedOrder.setOrderNumber(orderNumber);
                    else updatedOrder.setOrderNumber(existingOrder.get().getOrderNumber());
                    if (!description.isEmpty()) updatedOrder.setDescription(description);
                    else updatedOrder.setDescription(existingOrder.get().getDescription());
                    updatedOrder.setClientId(uuidService.uuidToBytes(userContext.getId()));
                    jpaOrderRepo.save(updatedOrder);
                    return HttpStatus.OK;
                } else {
                    return HttpStatus.BAD_REQUEST;
                }
            }
        } catch (Exception e) {
            throw new SuppressedStackTraceException(e.getMessage());
        }
    }

    public HttpStatus deleteOrder(String id) {
        try {
            Optional<Order> optionalOrder = jpaOrderRepo.findByIdOrder(id);
            if (optionalOrder.isPresent()) {
                jpaOrderRepo.delete(optionalOrder.get());
                return HttpStatus.OK;
            }

        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Transactional
    public HttpStatus deleteAllOrders() {
        try {
            jpaOrderRepo.deleteAllByClientId(uuidService.uuidToBytes(userContext.getId()));
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }
}

