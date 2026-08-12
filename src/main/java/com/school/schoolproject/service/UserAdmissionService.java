package com.school.schoolproject.service;

import com.school.schoolproject.entities.UserAdmission;
import com.school.schoolproject.repository.UserAdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAdmissionService {

    @Autowired
    private UserAdmissionRepository userAdmissionRepository;

    public boolean saveAdmissionForm(UserAdmission userAdmission) {
        try {
            this.userAdmissionRepository.save(userAdmission);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
