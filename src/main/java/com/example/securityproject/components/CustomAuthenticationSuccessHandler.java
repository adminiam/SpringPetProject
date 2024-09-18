package com.example.securityproject.components;

import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JpaClientRepo jpaClientRepo;
    @Autowired
    UserContext userContext;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = authentication.getName();
        Client clientData = jpaClientRepo.getClientByLoginName(username);
        userContext.setId(clientData.getIdClientUUID());
        userContext.setName(username);
        userContext.setRole(clientData.getRole());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContextHolder.getContext().setAuthentication(userContext);
        response.sendRedirect("home");
    }
}
