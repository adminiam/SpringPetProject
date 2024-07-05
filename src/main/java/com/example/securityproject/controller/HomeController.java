package com.example.securityproject.controller;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import com.example.securityproject.repository.JpaOrderRepo;
import com.example.securityproject.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;


@Controller
public class HomeController {
    @Autowired
    UserContext userContext;
    @Autowired
    JpaClientRepo jpaClientRepo;
    @Autowired
    ClientService clientService;
    @Autowired
    JpaOrderRepo jpaOrderRepo;

    @GetMapping("/home")
    public String getTrackingStatuses(Model model) {
        UUID uuid = userContext.getId();
        Client client = jpaClientRepo.getClientByIdClient(clientService.uuidToBytes(uuid));
        byte[] v = client.getIdClient();
        model.addAttribute("trackingNumbers", jpaOrderRepo.findAllByClientId(v));
        return "home";
    }
}
//        jpaClientRepo.findById(userContext.getId()).get().getId_client())