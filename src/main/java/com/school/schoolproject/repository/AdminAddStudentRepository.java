package com.school.schoolproject.repository;

import com.school.schoolproject.entities.AdminAddStudent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminAddStudentRepository extends JpaRepository<AdminAddStudent, Integer> {
    Optional<AdminAddStudent> findByRollNumber(int rollNumber);

    @Transactional
    @Modifying
    @Query("delete from AdminAddStudent s where s.rollNumber = :rollNumber")
    int deleteByRollNumber(@Param("rollNumber") int rollNumber);
}
