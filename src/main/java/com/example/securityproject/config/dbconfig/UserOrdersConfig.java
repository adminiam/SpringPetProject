package com.example.securityproject.config.dbconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class UserOrdersConfig {
    @Bean
    public DriverManagerDataSource configuration(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://us-cluster-east-01.k8s.cleardb.net:3306/heroku_dc3fec3c09bd99a");
        dataSource.setUsername(System.getenv("login"));
        dataSource.setPassword(System.getenv("pass"));
        return dataSource;
    }
}
