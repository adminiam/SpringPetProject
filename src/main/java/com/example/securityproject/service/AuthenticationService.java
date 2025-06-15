package com.example.securityproject.service;

import com.example.securityproject.components.JwtTokenProvider;
import com.example.securityproject.dto.LoginRequest;
import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final JpaClientRepo clientRepo;

    public AuthenticationService(JwtTokenProvider jwtTokenProvider,
                                 AuthenticationManager authenticationManager,
                                 JpaClientRepo clientRepo) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.clientRepo = clientRepo;
    }

    public ResponseEntity<?> login(LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Cookie accessCookie = new Cookie("accessToken", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(15 * 60);
        response.setHeader("Set-Cookie", String.format("%s=%s; Path=/; HttpOnly; SameSite=Lax; Max-Age=%d", 
            "accessToken", accessToken, 15 * 60));

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addHeader("Set-Cookie", String.format("%s=%s; Path=/; HttpOnly; SameSite=Lax; Max-Age=%d", 
            "refreshToken", refreshToken, 7 * 24 * 60 * 60));

        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        Client client = clientRepo.getClientByLoginName(username);

        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        UserDetails userDetails =
                User.withUsername(client.getLoginName())
                        .password(client.getPassword())
                        .authorities(new SimpleGrantedAuthority(client.getRole()))
                        .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);

        Cookie accessCookie = new Cookie("accessToken", newAccessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(15 * 60);

        response.setHeader("Set-Cookie", String.format("%s=%s; Path=/; HttpOnly; SameSite=Lax; Max-Age=%d", 
            "accessToken", newAccessToken, 15 * 60));

        return ResponseEntity.ok(Map.of("message", "Access token refreshed"));
    }

    public ResponseEntity<?> logout(HttpServletResponse response) {
        response.setHeader("Set-Cookie", "accessToken=; Path=/; HttpOnly; SameSite=Lax; Max-Age=0");
        response.addHeader("Set-Cookie", "refreshToken=; Path=/; HttpOnly; SameSite=Lax; Max-Age=0");

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
