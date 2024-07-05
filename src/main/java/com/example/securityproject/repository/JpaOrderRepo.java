package com.example.securityproject.repository;

import com.example.securityproject.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface JpaOrderRepo extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.clientId = :clientId")
    List<Order> findAllByClientId(byte[] clientId);
}
