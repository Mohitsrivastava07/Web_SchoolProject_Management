package com.school.schoolproject.repository;

import com.school.schoolproject.entities.AdminAdmission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminAdmissionRepository extends JpaRepository<AdminAdmission, Integer> {

    List<AdminAdmission> findById(int id);

    @Transactional
    @Modifying
    @Query("delete from AdminAdmission i where i.id = :id")
    int deleteAdmissionById(@Param("id") int id);
}
