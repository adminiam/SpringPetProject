package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.entities.Order;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaOrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

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

    public RedirectView createOrder(String email, String orderNumber, String description) {
        try {
            Order order = new Order();
            order.setIdOrder(UUID.randomUUID().toString());
            order.setEmail(email);
            order.setOrderNumber(orderNumber);
            order.setDescription(description);
            order.setClientId(uuidService.uuidToBytes(userContext.getId()));
            jpaOrderRepo.save(order);
            return new RedirectView("/home", true);
        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

    public RedirectView updateOrder(String id, String email, String orderNumber, String description) {
        try {
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
                return new RedirectView("/home", true);
            } else {
                return new RedirectView("/error", true);
            }
        } catch (DataAccessException e) {
            return new RedirectView("/error", true);
        } catch (Exception e) {
            return new RedirectView("/error", true);
        }
    }

    public RedirectView deleteOrder(String id) {
        try {
            Optional<Order> optionalOrder = jpaOrderRepo.findByIdOrder(id);
            if (optionalOrder.isPresent()) {
                jpaOrderRepo.delete(optionalOrder.get());
                return new RedirectView("/home", true);
            }

        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
        return new RedirectView("/error", true);
    }

    @Transactional
    public RedirectView deleteAllOrders() {
        try {
            jpaOrderRepo.deleteAllByClientId(uuidService.uuidToBytes(userContext.getId()));
            return new RedirectView("/home", true);
        } catch (DataAccessException e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }

}
