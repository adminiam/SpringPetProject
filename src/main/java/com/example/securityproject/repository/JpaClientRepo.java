package com.example.securityproject.repository;


import com.example.securityproject.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaClientRepo extends JpaRepository<Client,byte[]> {
    @Query("select c.loginName from Client c")

    @Query("select c.password from Client c where c.loginName = :username")
    String getClientPasswordByLoginName(@Param("username") String username);

    Client getClientByLoginName(String name);

    Client getClientByIdClient(byte[] idClient);
}
