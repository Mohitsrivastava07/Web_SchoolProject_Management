package com.school.schoolproject.service;

import com.school.schoolproject.entities.AdminAdmission;
import com.school.schoolproject.repository.AdminAdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAdmissionService {

    @Autowired
    private AdminAdmissionRepository adminAdmissionRepository;

    public boolean saveAdmissionForm(AdminAdmission adminAdmission) {
        try {
            this.adminAdmissionRepository.save(adminAdmission);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
