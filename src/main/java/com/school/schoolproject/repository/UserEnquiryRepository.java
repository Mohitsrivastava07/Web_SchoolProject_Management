package com.school.schoolproject.repository;

import com.school.schoolproject.entities.UserEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEnquiryRepository extends JpaRepository<UserEnquiry, Integer> {
}
