package com.school.schoolproject.repository;

import com.school.schoolproject.entities.UserAdmission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAdmissionRepository extends JpaRepository<UserAdmission, Integer> {

    @Transactional
    @Modifying
    @Query("delete from UserAdmission i where i.id = :id")
    int deleteAdmissionById(@Param("id") int id);
}
