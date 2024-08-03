package com.example.securityproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    private byte[] id_order;
    @Column(name = "email")
    private String email;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "description")
    private String description;
    @Column(name = "id_client")
    private byte[] clientId;
    public void setIdClientUUID(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        this.id_order = byteBuffer.array();
    }
    @Override
    public String toString() {
        return email +
                ", " + orderNumber +
                ", " + description ;
    }
}
