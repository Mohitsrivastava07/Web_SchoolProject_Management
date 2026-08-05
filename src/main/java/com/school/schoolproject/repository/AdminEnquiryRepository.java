package com.school.schoolproject.repository;

import com.school.schoolproject.entities.AdminEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminEnquiryRepository extends JpaRepository<AdminEnquiry, Integer> {
}
