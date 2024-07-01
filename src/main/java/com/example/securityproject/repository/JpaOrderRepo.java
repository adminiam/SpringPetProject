package com.example.securityproject.repository;

import com.example.securityproject.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepo extends JpaRepository<Order,Long> {
}
