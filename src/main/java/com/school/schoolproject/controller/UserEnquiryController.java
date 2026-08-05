package com.school.schoolproject.controller;

import com.school.schoolproject.entities.UserEnquiry;
import com.school.schoolproject.repository.UserEnquiryRepository;
import com.school.schoolproject.service.UserEnquiryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserEnquiryController {

    @Autowired
    private UserEnquiryService userEnquiryService;

    @Autowired
    private UserEnquiryRepository userEnquiryRepository;

    @PostMapping("/userSubmitEnquiryForm")
    public ResponseEntity<String> submitEnquiryForm(@Valid @ModelAttribute UserEnquiry userEnquiry) {
        boolean submit = this.userEnquiryService.saveEnquiryForm(userEnquiry);
        if(!submit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Enquiry form not submitted!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Enquiry form submitted!");
    }

    //API to count the total enquiry
    @GetMapping("/api/enquiry/count")
    @ResponseBody
    public Map<String, Long> getEnquiryCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", userEnquiryRepository.count());
        return response;
    }
}
