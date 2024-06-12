package com.example.securityproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    private int id_order;
    @Column(name = "email")
    private String email;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return email +
                ", " + orderNumber +
                ", " + description ;
    }
}
