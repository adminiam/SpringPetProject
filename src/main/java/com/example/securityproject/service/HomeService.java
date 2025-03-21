package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.entities.Client;
import com.example.securityproject.entities.Order;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import com.example.securityproject.repository.JpaOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HomeService {
    @Autowired
    UserContext userContext;
    @Autowired
    JpaClientRepo jpaClientRepo;
    @Autowired
    UUIDService uuidService;
    @Autowired
    JpaOrderRepo jpaOrderRepo;

    public List<Order> getTrackingStatuses() {
        try {
            UUID uuid = userContext.getId();
            Client client = jpaClientRepo.getClientByIdClient(uuidService.uuidToBytes(uuid));
            byte[] v = client.getIdClient();

            return jpaOrderRepo.findAllByClientId(v);
        }catch (Exception e){
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }
}
