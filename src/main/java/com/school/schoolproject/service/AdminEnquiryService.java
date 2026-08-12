package com.school.schoolproject.service;

import com.school.schoolproject.entities.AdminEnquiry;
import com.school.schoolproject.repository.AdminEnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminEnquiryService {

    @Autowired
    private AdminEnquiryRepository adminEnquiryRepository;

    public boolean saveEnquiryForm(AdminEnquiry adminEnquiry) {
        try {
            this.adminEnquiryRepository.save(adminEnquiry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}