package com.example.securityproject.models;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "actions")
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    @Id
    private long id_action;
    private String action;
    private String methodName;
    private String result;
}
