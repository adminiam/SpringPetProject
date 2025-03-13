package com.example.securityproject.config.securityConfig;

import com.example.securityproject.config.dbconfig.CustomArgon2PasswordEncoder;
import com.example.securityproject.entities.Client;
import com.example.securityproject.repository.JpaClientRepo;
import com.example.securityproject.components.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final JpaClientRepo jpaClientRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(JpaClientRepo jpaClientRepo, JwtTokenProvider jwtTokenProvider) {
        this.jpaClientRepo = jpaClientRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomArgon2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/register", "/error").permitAll()
                        .requestMatchers("/adminPanel/*", "/getMessage").hasAuthority("ADMIN")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService()),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Client client = jpaClientRepo.getClientByLoginName(username);
            if (client == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            return User.withUsername(username)
                    .password(client.getPassword())
                    .authorities(new SimpleGrantedAuthority(client.getRole()))
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}