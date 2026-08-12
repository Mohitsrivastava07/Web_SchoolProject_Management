package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminContact;
import com.school.schoolproject.service.AdminContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AdminContactController {

    @Autowired
    private AdminContactService adminContactService;

    @PostMapping("/submitContactForm")
    public ResponseEntity<String> submitContactForm(@Valid @ModelAttribute AdminContact adminContact) {
        boolean submit = this.adminContactService.saveContactForm(adminContact);
        if (!submit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contact form not submitted!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact form submitted!");
    }
}
