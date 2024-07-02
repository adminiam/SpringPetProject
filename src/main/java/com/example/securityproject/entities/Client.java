package com.example.securityproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "clients")
public class Client {
    @Id
    @Column(name="id_client")
    Long id_client;
    @Column(name = "login_name")
    String loginName;
    @Column(name = "password")
    String password;
}
