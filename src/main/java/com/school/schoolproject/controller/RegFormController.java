package com.school.schoolproject.controller;

import com.school.schoolproject.entities.RegForm;
import com.school.schoolproject.repository.RegFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegFormController {

    @Autowired
    private RegFormRepository regFormRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String regUserForm(@ModelAttribute RegForm regForm) {
        regForm.setPassword(passwordEncoder.encode(regForm.getPassword()));
        if (!StringUtils.hasText(regForm.getRoles())) {
            regForm.setRoles("USER");
        }
        regFormRepository.save(regForm);
        return "redirect:/login?registered=true";
    }
}
