package com.school.schoolproject.service;

import com.school.schoolproject.entities.UserContact;
import com.school.schoolproject.repository.UserContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactService {

    @Autowired
    private UserContactRepository userContactRepository;

    public boolean saveContactForm(UserContact userContact) {
        try {
            this.userContactRepository.save(userContact);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
