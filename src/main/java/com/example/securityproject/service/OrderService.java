package com.example.securityproject.service;

import com.example.securityproject.entities.Order;
import com.example.securityproject.repository.JpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    JpaRepo jpaRepo;

    public List<Order> getAllOrders(){
       return jpaRepo.findAll();
    }
}
