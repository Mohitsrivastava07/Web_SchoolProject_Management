package com.school.schoolproject.repository;

import com.school.schoolproject.entities.AdminAddTeacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminAddTeacherRepository extends JpaRepository<AdminAddTeacher, String> {
    Optional<AdminAddTeacher> findByEmployeeId(String employeeId);

    Optional<AdminAddTeacher> findTopByOrderByEmployeeIdDesc();

    @Transactional
    @Modifying
    @Query("delete from AdminAddTeacher e where e.employeeId = :employeeId")
    int deleteByEmpId(@Param("employeeId") String employeeId);
}
