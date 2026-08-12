package com.school.schoolproject.controller;

import com.school.schoolproject.entities.UserEnquiry;
import com.school.schoolproject.repository.UserEnquiryRepository;
import com.school.schoolproject.service.UserEnquiryService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/check/enquiry/all")
    public String showAllEnquiryData(Model model, RedirectAttributes redirectAttributes) {
        List<UserEnquiry> foundEnquiry = this.userEnquiryRepository.findAll();
        model.addAttribute("enquiries", foundEnquiry);
        model.addAttribute("totalEnquiries", foundEnquiry != null ? foundEnquiry.size() : 0);
       if (foundEnquiry.isEmpty()) {
           redirectAttributes.addFlashAttribute("Error! Not Enquiry founded");
           return "redirect:/admin/home";
       }
       return "show_all_enquiries";
    }
}
