package com.example.securityproject.repository;

import com.example.securityproject.entities.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface JpaOrderRepo extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.clientId = :clientId")
    List<Order> findAllByClientId(byte[] clientId);
    void deleteAllByClientId(byte [] id);
    Optional<Order> findByIdOrder(String idOrder);
}
