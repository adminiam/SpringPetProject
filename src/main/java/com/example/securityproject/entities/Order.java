package com.example.securityproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    private String idOrder;
    @Column(name = "email")
    private String email;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "description")
    private String description;
    @Column(name = "id_client")
    private byte[] clientId;

    @Override
    public String toString() {
        return email +
                ", " + orderNumber +
                ", " + description;
    }
}
