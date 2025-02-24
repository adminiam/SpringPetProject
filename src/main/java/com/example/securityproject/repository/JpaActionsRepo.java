package com.example.securityproject.repository;

import com.example.securityproject.models.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaActionsRepo extends JpaRepository<Action, Long> {

}
