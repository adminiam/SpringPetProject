package com.example.securityproject.service;

import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistryService {
    @Autowired
    PasswordEncoderService encoderService;
    @Autowired
    JpaClientRepo clientRepo;

    public String register(String name, String password) {
        if (!name.isEmpty() && !password.isEmpty()) {
            if (clientRepo.getClientByLoginName(name) != null) {
//                String errorMessage = URLEncoder.encode("This user already exists", StandardCharsets.UTF_8);
                return "redirect:/error";
            } else {
                Client client = new Client();
                client.setIdClientUUID(UUID.randomUUID());
                client.setLoginName(name);
                client.setPassword(encoderService.generatePassword(password));
                clientRepo.save(client);
                return "redirect:/login";
            }
        } else {
//            String errorMessage = URLEncoder.encode("Error occurred", StandardCharsets.UTF_8);
            return "redirect:/error";
        }
    }

}
