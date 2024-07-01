package com.example.securityproject.repository;


import com.example.securityproject.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaClientRepo extends JpaRepository<Client, Long> {
    @Query("select c.login_name from Client c")
    List<String> getClientsNames();
    @Query("select c.password from Client c where c.login_name = :username")
    String getClientPasswordByLoginName(@Param("username") String username);
}
