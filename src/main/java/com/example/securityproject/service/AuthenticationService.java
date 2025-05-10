package com.example.securityproject.service;

import com.example.securityproject.components.JwtTokenProvider;
import com.example.securityproject.components.UserContext;
import com.example.securityproject.dto.LoginRequest;
import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaClientRepo jpaClientRepo;

    @Autowired
    private UserContext userContext;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> login(LoginRequest request) {
        Client clientData = jpaClientRepo.getClientByLoginName(request.getUsername());
        if (clientData == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            userContext.setId(clientData.getIdClientUUID());
            userContext.setName(request.getUsername());
            userContext.setRole(clientData.getRole());

            String token = jwtTokenProvider.generateToken(authentication);

            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(60 * 60)
                    .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Authentication failed");
        }
    }
}
