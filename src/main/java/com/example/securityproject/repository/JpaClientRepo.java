package com.example.securityproject.repository;

import com.example.securityproject.entities.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClientRepo extends JpaRepository<Client, byte[]> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Client c WHERE c.role = 'OWNER'")
    void deleteAllAdmins();

    Client getClientByLoginName(String name);

    Client getClientByIdClient(byte[] idClient);
}
