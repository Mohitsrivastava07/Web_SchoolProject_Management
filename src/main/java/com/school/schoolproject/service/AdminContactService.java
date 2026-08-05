package com.school.schoolproject.service;

import com.school.schoolproject.entities.AdminContact;
import com.school.schoolproject.repository.AdminContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminContactService {

    @Autowired
    private AdminContactRepository adminContactRepository;

    public boolean saveContactForm(AdminContact adminContact) {
        try {
            this.adminContactRepository.save(adminContact);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
