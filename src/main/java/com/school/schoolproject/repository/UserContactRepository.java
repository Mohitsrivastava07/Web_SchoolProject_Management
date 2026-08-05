package com.school.schoolproject.repository;

import com.school.schoolproject.entities.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Integer> {
}
