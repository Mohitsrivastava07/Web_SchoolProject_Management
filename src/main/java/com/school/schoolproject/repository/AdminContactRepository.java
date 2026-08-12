package com.school.schoolproject.repository;

import com.school.schoolproject.entities.AdminContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminContactRepository extends JpaRepository<AdminContact, Integer> {
}
