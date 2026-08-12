package com.school.schoolproject.service;

import com.school.schoolproject.entities.UserEnquiry;
import com.school.schoolproject.repository.UserEnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEnquiryService {

    @Autowired
    private UserEnquiryRepository userEnquiryRepository;

    public boolean saveEnquiryForm(UserEnquiry userEnquiry) {
        try {
            this.userEnquiryRepository.save(userEnquiry);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
