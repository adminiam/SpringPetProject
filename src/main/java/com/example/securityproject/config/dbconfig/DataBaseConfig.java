package com.example.securityproject.config.dbconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfig {
    @Bean
    public DriverManagerDataSource configuration() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3307/aliOrders");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        return dataSource;
    }
}

