package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminEnquiry;
import com.school.schoolproject.service.AdminEnquiryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AdminEnquiryController {

    @Autowired
    private AdminEnquiryService adminEnquiryService;

    @PostMapping("/submitEnquiryForm")
    public ResponseEntity<String> submitEnquiryForm(@Valid @ModelAttribute AdminEnquiry adminEnquiry) {
        boolean submit = this.adminEnquiryService.saveEnquiryForm(adminEnquiry);
        if (!submit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Enquiry form not submitted!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Enquiry form submitted!");
    }
}
