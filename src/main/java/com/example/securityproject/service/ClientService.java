package com.example.securityproject.service;

import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class ClientService {
    public byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }
}
