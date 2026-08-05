package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAdmission;
import com.school.schoolproject.entities.UserAdmission;
import com.school.schoolproject.repository.UserAdmissionRepository;
import com.school.schoolproject.service.UserAdmissionService;
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
import java.util.Optional;

@Controller
public class UserAdmissionController {

    @Autowired
    private UserAdmissionService userAdmissionService;

    @Autowired
    private UserAdmissionRepository userAdmissionRepository;

    @PostMapping("/usersubmitAdmissionForm")
    public ResponseEntity<String> submitAdmissionForm(@Valid @ModelAttribute UserAdmission userAdmission) {
        boolean submit = this.userAdmissionService.saveAdmissionForm(userAdmission);
        if (!submit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Admission form not submitted!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Admission form submitted!");
    }

    // ================= OPEN UPDATE PAGE =================
    @GetMapping("/user/customize/admission/update")
    public String openStudentPage(
            @RequestParam("updateAdmission") int admissionId,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<UserAdmission> admission =
                userAdmissionRepository.findById(admissionId);
        if (admission.isPresent()) {
            model.addAttribute("admissionFound", admission.get());
            return "user_update_admission";
        }

        redirectAttributes.addFlashAttribute(
                "error",
                "No admission found with Roll Number : " + admissionId);
        return "redirect:/user/home";
    }

    // ================= UPDATE ADMISSION =================
    @PostMapping("/userUpdateSubmitAdmissionForm")
    public ResponseEntity<String> submitUpdateAdmissionPage(@ModelAttribute AdminAdmission form) {

        Optional<UserAdmission> admission =
                userAdmissionRepository.findById(form.getId());
        if (admission.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Admission not found with admission id : "
                            + form.getId());
        }

        UserAdmission existing = admission.get();

        existing.setStudentName(form.getStudentName());
        existing.setDateOfBirth(form.getDateOfBirth());
        existing.setGender(form.getGender());
        existing.setNationality(form.getNationality());
        existing.setGrade(form.getGrade());
        existing.setAcademicYear(form.getAcademicYear());
        existing.setParentName(form.getParentName());
        existing.setRelationShip(form.getRelationShip());
        existing.setParentEmailId(form.getParentEmailId());
        existing.setParentPhoneNumber(form.getParentPhoneNumber());
        existing.setAddress(form.getAddress());
        existing.setPreviousSchool(form.getPreviousSchool());
        existing.setAdditionalMessage(form.getAdditionalMessage());

        boolean updated = userAdmissionService.saveAdmissionForm(existing);
        if (!updated) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Admission update failed!");
        }
        return ResponseEntity.ok("Admission updated successfully!");
    }

    @GetMapping("/user/customize/admission/search")
    public ResponseEntity<Optional<UserAdmission>> searchByAdmissionId(@RequestParam("admissionId") int id,
                                                                        Model model) {
        Optional<UserAdmission> searchAdmission = this.userAdmissionRepository.findById(id);
        if (searchAdmission != null) {
            return ResponseEntity.ok(searchAdmission);
        }
        return ResponseEntity.notFound().build();
    }

    //API to count the total admission
    @GetMapping("/api/admission/count")
    @ResponseBody
    public Map<String, Long> getAdmissionCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", userAdmissionRepository.count());
        return response;
    }
}
