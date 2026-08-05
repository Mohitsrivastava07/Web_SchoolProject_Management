package com.school.schoolproject.repository;

import com.school.schoolproject.entities.RegForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegFormRepository extends JpaRepository<RegForm, Integer> {
    Optional<RegForm> findByUsername(String username);
}
