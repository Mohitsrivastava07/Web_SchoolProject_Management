package com.school.schoolproject.controller;

import com.school.schoolproject.entities.UserContact;
import com.school.schoolproject.repository.UserContactRepository;
import com.school.schoolproject.service.UserContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserContactController {

    @Autowired
    private UserContactService userContactService;

    @Autowired
    private UserContactRepository userContactRepository;

    @PostMapping("/usersubmitContactForm")
    public ResponseEntity<String> submitContactForm(@Valid @ModelAttribute UserContact userContact) {
        boolean submit = this.userContactService.saveContactForm(userContact);
        if (!submit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contact form is not submitted!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact form is submitted!");
    }

    //API to count the total contact
    @GetMapping("/api/contact/count")
    @ResponseBody
    public Map<String, Long> getContactCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", userContactRepository.count());
        return response;
    }

    @GetMapping("/check/contact/all")
    public String getAllContactData(Model model, RedirectAttributes redirectAttributes) {
        List<UserContact> listContact = this.userContactRepository.findAll();
        model.addAttribute("contacts", listContact);
        model.addAttribute("totalContacts", listContact != null ? listContact.size() : 0);

        if (listContact.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No Contacts Founded!");
            return "redirect:/admin/home";
        }
        return "show_all_contacts";
    }
}
