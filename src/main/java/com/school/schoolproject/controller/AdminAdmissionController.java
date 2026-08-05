package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAddStudent;
import com.school.schoolproject.entities.AdminAdmission;
import com.school.schoolproject.entities.UserAdmission;
import com.school.schoolproject.repository.AdminAdmissionRepository;
import com.school.schoolproject.repository.UserAdmissionRepository;
import com.school.schoolproject.service.AdminAdmissionService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminAdmissionController {

    @Autowired
    private AdminAdmissionService adminAdmissionService;

    @Autowired
    private AdminAdmissionRepository adminAdmissionRepository;

    @Autowired
    private UserAdmissionRepository userAdmissionRepository;

    @PostMapping("/submitAdmissionForm")
    public ResponseEntity<String> submitAdmissionForm(@Valid @ModelAttribute AdminAdmission adminAdmission) {
        boolean submit = this.adminAdmissionService.saveAdmissionForm(adminAdmission);
        if (!submit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Admission form not submitted!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Admission form submitted!");
    }

    // ================= OPEN UPDATE PAGE =================
    @GetMapping("/admin/customize/admission/update")
    public String openStudentPage(
            @RequestParam("updateAdmission") int admissionId,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<AdminAdmission> admission =
                adminAdmissionRepository.findById(admissionId);
        if (admission.isPresent()) {
            model.addAttribute("admissionFound", admission.get());
            return "admin_update_admission";
        }

        redirectAttributes.addFlashAttribute(
                "error",
                "No admission found with Roll Number : " + admissionId);
        return "redirect:/admin/home";
    }

    // ================= UPDATE ADMISSION =================
    @PostMapping("/updateSubmitAdmissionForm")
    public ResponseEntity<String> submitUpdateAdmissionPage(@ModelAttribute AdminAdmission form) {

        Optional<AdminAdmission> admission =
                adminAdmissionRepository.findById(form.getId());
        if (admission.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Admission not found with admission id : "
                            + form.getId());
        }

        AdminAdmission existing = admission.get();

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

        boolean updated = adminAdmissionService.saveAdmissionForm(existing);
        if (!updated) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Admission update failed!");
        }
        return ResponseEntity.ok("Admission updated successfully!");
    }

    //search admission by admin
    @GetMapping("/admin/customize/admission/search")
    public ResponseEntity<Optional<AdminAdmission>> searchByAdmissionId(@RequestParam("admissionId") int id,
                                                                        Model model) {
        Optional<AdminAdmission> searchAdmission = this.adminAdmissionRepository.findById(id);
        if (searchAdmission != null) {
            return ResponseEntity.ok(searchAdmission);
        }
        return ResponseEntity.notFound().build();
    }

    //search admission by user
    @GetMapping("/admin/customize/admission/search/byUser")
    public ResponseEntity<Optional<UserAdmission>> searchByAdmissionIdByUser(@RequestParam("admissionId") int id,
                                                                             Model model) throws Exception {
        Optional<UserAdmission> searchAdmissionByUser = this.userAdmissionRepository.findById(id);
        if (searchAdmissionByUser != null) {
            return ResponseEntity.ok(searchAdmissionByUser);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/customize/admission/delete")
    public ResponseEntity<String> deleteAdmissionById(@RequestParam("deleteAdmission") int id,
                                                      Model model) {
        int affectedRows = this.adminAdmissionRepository.deleteAdmissionById(id);
        if (affectedRows > 0) {
            return ResponseEntity.ok("Admission deleted successfully! " + affectedRows + " row affected.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admission Not Found!");
    }

    // show all admission by admin
    @GetMapping("/admin/customize/admission/all")
    public ResponseEntity<List<AdminAdmission>> showAllAdmission(Model model) {
        List<AdminAdmission> listAdmission = this.adminAdmissionRepository.findAll();
        return ResponseEntity.ok(listAdmission);
    }

    // show all admission by user
    @GetMapping("/admin/customize/admission/all/byUser")
    public ResponseEntity<List<UserAdmission>> showAllAdmissionByUser(Model model) {
        List<UserAdmission> listAdmissionByUser = this.userAdmissionRepository.findAll();
        return ResponseEntity.ok(listAdmissionByUser);
    }
}
