package com.example.securityproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "clients")
public class Client {
    @Id
    @Column(name="id_client")
    private byte[] idClient;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "password")
    private String password;

    public UUID getIdClientUUID() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(idClient);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }

    public void setIdClientUUID(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        this.idClient = byteBuffer.array();
    }
}
