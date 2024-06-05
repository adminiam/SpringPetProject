package com.example.securityproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Order {
    @Id
    @Column(name = "id_order")
    private String id_order;
    @Column(name = "name_order")
    private String name_order;
}
