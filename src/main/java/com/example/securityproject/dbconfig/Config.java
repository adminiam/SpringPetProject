package com.example.securityproject.dbconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class Config {
    @Bean
    public DriverManagerDataSource configuration(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/aliOrders");
        dataSource.setUsername("root");
        dataSource.setPassword("Zxcvb122");
        return dataSource;
    }
}
