package com.school.schoolproject.repository;

import com.school.schoolproject.entities.AdminAddStudent;
import com.school.schoolproject.entities.AdminMarksheetData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminMarksheetDataRepository extends JpaRepository<AdminMarksheetData, Integer> {

    Optional<AdminMarksheetData> findByStudentNameAndRollNumberAndEmailId(String studentName, int rollNumber, String emailId);
}
