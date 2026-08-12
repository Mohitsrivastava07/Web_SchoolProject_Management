package com.school.schoolproject.repository;

import com.school.schoolproject.entities.Marksheet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksheetRepository extends JpaRepository<Marksheet, Integer> {
    List<Marksheet> findByAdminMarksheetData_RollNumber(int rollNumber);


    @Query(value = """
            SELECT
                m.id,
                a.roll_number,
                a.student_name,
                a.grade,
                a.email_id,
                m.file_name,
                m.marksheet_type,
                m.file_data
            FROM admin_marksheet_data a
            JOIN marksheet m
                ON m.admin_marksheet_data_id = a.roll_number
            ORDER BY m.id ASC
            """, nativeQuery = true)
    List<Object[]> findAllMarksheetData();
}
