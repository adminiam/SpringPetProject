package com.example.securityproject.security;

import com.example.securityproject.repository.JpaClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
@Autowired
  JpaClientRepo jpaClientRepo;
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/login", "/style.css").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/home", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
            );
    return http.build();
  }


  @Bean
  public UserDetailsService userDetailsService(){
    List<String> clientNames = jpaClientRepo.getClientsNames();

    List<UserDetails> users = clientNames.stream()
            .map(username -> User.withDefaultPasswordEncoder()
                    .username(username)
                    .password(jpaClientRepo.getClientPasswordByLoginName(username))
                    .roles("USER")
                    .build())
            .collect(Collectors.toList());
    System.out.println(jpaClientRepo.getClientPasswordByLoginName("user"));
    return new InMemoryUserDetailsManager(users);
  }
}
