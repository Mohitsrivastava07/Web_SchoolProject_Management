package com.school.schoolproject.controller;

import com.school.schoolproject.entities.RegForm;
import com.school.schoolproject.repository.RegFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/check/regist/byUsername")
    public String checkRegistrationDetailsByUsername(@RequestParam("username") String username,
                                                     Model model, RedirectAttributes redirectAttributes) {
        List<RegForm> foundUsername = this.regFormRepository.findByUsername(username);
        model.addAttribute("registrations", foundUsername);
        model.addAttribute("totalRegistrations", foundUsername != null ? foundUsername.size() : 0);
        if (foundUsername.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No registration found with username: " + username);
            return "redirect:/admin/home";
        }
        return "show_all_registrations";
    }

    @GetMapping("/update/userToadmin")
    public ResponseEntity<String> updateUserToAdminRoles(@RequestParam("username") String username) {
        int foundUsername = this.regFormRepository.setUserToAdmin(username);
        if (foundUsername > 0) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated Successfully, One rows affected!");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/check/regist/all")
    public String showAllRegistrationData(Model model, RedirectAttributes redirectAttributes) {
        List<RegForm> listRegistrationData = this.regFormRepository.findAll();
        model.addAttribute("registrations", listRegistrationData);
        model.addAttribute("totalRegistrations", listRegistrationData != null ? listRegistrationData.size() : 0);
        if (listRegistrationData.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No registration founded");
            return "redirect:/admin/home";
        }
        return "show_all_registrations";
    }

    @GetMapping("/check/regist/all/admin")
    public String showAllAdminData(Model model, RedirectAttributes redirectAttributes) {
        List<RegForm> listAllAdminData = this.regFormRepository.getAllAdmin();
        model.addAttribute("registrations", listAllAdminData);
        model.addAttribute("totalRegistrations", listAllAdminData != null ? listAllAdminData.size() : 0);
        if (listAllAdminData.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No registration founded by ADMIN");
            return "redirect:/admin/home";
        }
        return "show_all_registrations";
    }
}
