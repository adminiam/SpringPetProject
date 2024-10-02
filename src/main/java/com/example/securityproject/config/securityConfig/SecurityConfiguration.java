package com.example.securityproject.config.securityConfig;

import com.example.securityproject.components.CustomAuthenticationSuccessHandler;

import com.example.securityproject.repository.JpaClientRepo;
import com.example.securityproject.service.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.atomic.AtomicReference;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    JpaClientRepo jpaClientRepo;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    PasswordEncoderService passwordEncoderService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/adminPanel/*").hasAuthority("ADMIN")
                        .requestMatchers("/login", "/styles.css", "/register", "/error", "/main.js","/images/*","/send").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .rememberMe((rememberMe) -> rememberMe.userDetailsService(userDetailsService())
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(259200));

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            String password = jpaClientRepo.getClientPasswordByLoginName(username);
            String role = jpaClientRepo.getClientByLoginName(username).getRole();
            System.out.println(role);
            return User.withUsername(username)
                    .password(password)
                    .authorities(new SimpleGrantedAuthority(role))
                    .build();
        };
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        AtomicReference<String> userName = new AtomicReference<>("");
        auth.userDetailsService((username) -> {
            String password = jpaClientRepo.getClientPasswordByLoginName(username);
            String role = jpaClientRepo.getClientByLoginName(username).getRole();
            System.out.println("User: " + username + ", Role: " + role);
            userName.set(username);
            return User.withUsername(username)
                    .password(password)
                    .authorities(new SimpleGrantedAuthority(role))
                    .build();
        }).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return passwordEncoderService.generatePassword(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return passwordEncoderService.passwordVerify(userName.get(), rawPassword.toString());
            }
        });
    }

}
