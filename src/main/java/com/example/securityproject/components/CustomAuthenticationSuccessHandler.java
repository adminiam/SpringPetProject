package com.example.securityproject.components;

import com.example.securityproject.repository.JpaClientRepo;
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
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        String username = authentication.getName();
        userContext.setId(jpaClientRepo.getClientByLoginName(username).getId_client());
        userContext.setName(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContextHolder.getContext().setAuthentication(userContext);

        response.sendRedirect("/home?id=" + jpaClientRepo.getClientByLoginName(username).getId_client());
    }
}
