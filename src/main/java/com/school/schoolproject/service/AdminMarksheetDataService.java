package com.school.schoolproject.service;

import com.school.schoolproject.entities.AdminMarksheetData;
import com.school.schoolproject.repository.AdminMarksheetDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminMarksheetDataService {

    private static final Logger log = LoggerFactory.getLogger(AdminMarksheetDataService.class);

    @Autowired
    private AdminMarksheetDataRepository adminMarksheetDataRepository;

    public boolean saveStudentMarksheetData(AdminMarksheetData adminMarksheetData) {
      try {
          this.adminMarksheetDataRepository.save(adminMarksheetData);
          return true;
      } catch (Exception e) {
          log.error("Failed to save the student data with marksheet: {}", e.getMessage(), e);
          return false;
      }
    }
}
