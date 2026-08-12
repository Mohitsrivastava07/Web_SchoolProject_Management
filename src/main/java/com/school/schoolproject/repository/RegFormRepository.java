package com.school.schoolproject.repository;

import com.school.schoolproject.entities.RegForm;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegFormRepository extends JpaRepository<RegForm, Integer> {
    List<RegForm> findByUsername(String username);

    @Query("from RegForm r where r.roles = 'ADMIN'")
    List<RegForm> getAllAdmin();

    @Transactional
    @Modifying
    @Query("update RegForm u set u.roles = 'ADMIN' where u.username = :username")
    int setUserToAdmin(String username);
}
