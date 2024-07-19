package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import com.example.securityproject.repository.JpaOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.UUID;

@Service
public class HomeService {
    @Autowired
    UserContext userContext;
    @Autowired
    JpaClientRepo jpaClientRepo;
    @Autowired
    ClientService clientService;
    @Autowired
    JpaOrderRepo jpaOrderRepo;
    public void getTrackingStatuses(Model model) {
        UUID uuid = userContext.getId();
        Client client = jpaClientRepo.getClientByIdClient(clientService.uuidToBytes(uuid));
        byte[] v = client.getIdClient();
        model.addAttribute("trackingNumbers", jpaOrderRepo.findAllByClientId(v));
    }
}
