package com.school.schoolproject.service;

import com.school.schoolproject.entities.AdminAddStudent;
import com.school.schoolproject.repository.AdminAddStudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAddStudentService {

    private static final Logger log = LoggerFactory.getLogger(AdminAddStudentService.class);

    @Autowired
    private AdminAddStudentRepository adminAddStudentRepository;

    public boolean saveAddStudent(AdminAddStudent adminAddStudent) {
        try {
            this.adminAddStudentRepository.save(adminAddStudent);
            return true;
        } catch (Exception e) {
            log.error("Failed to save student: {}", e.getMessage(), e);
            return false;
        }
    }
}