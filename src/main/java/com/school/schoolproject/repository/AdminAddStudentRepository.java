package com.school.schoolproject.repository;

import com.school.schoolproject.entities.AdminAddStudent;
import com.school.schoolproject.entities.Grade;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminAddStudentRepository extends JpaRepository<AdminAddStudent, Integer> {
    List<AdminAddStudent> findByRollNumber(int rollNumber);

    List<AdminAddStudent> findStudentByGrade(Grade grade);

    @Transactional
    @Modifying
    @Query("delete from AdminAddStudent s where s.rollNumber = :rollNumber")
    int deleteByRollNumber(@Param("rollNumber") int rollNumber);
}
