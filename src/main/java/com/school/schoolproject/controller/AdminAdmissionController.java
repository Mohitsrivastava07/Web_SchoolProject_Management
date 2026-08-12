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

        List<AdminAdmission> admission =
                adminAdmissionRepository.findById(admissionId);
        if (admission.isEmpty()) {
            model.addAttribute("admissionFound", admission.get(0));
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

        List<AdminAdmission> admission =
                adminAdmissionRepository.findById(form.getId());
        if (admission.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Admission not found with admission id : "
                            + form.getId());
        }

        AdminAdmission existing = admission.get(0);

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
    public String searchByAdmissionId(@RequestParam("admissionId") int id,
                                                                        Model model, RedirectAttributes redirectAttributes) {
        List<AdminAdmission> searchAdmission = this.adminAdmissionRepository.findById(id);
        model.addAttribute("admissions", searchAdmission);
        model.addAttribute("totalAdmissions", searchAdmission.size());
        if (searchAdmission.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No Admission founded by id: " + id);
            return "redirect:/admin/home";
        }
        return "show_all_admissions";
    }

    //search admission by user
    @GetMapping("/admin/customize/admission/search/byUser")
    public String searchByAdmissionIdByUser(@RequestParam("admissionId") int id,
                                                                             Model model, RedirectAttributes redirectAttributes) throws Exception {
        List<UserAdmission> searchAdmissionByUser = this.userAdmissionRepository.findById(id);
        model.addAttribute("admissions", searchAdmissionByUser);
        model.addAttribute("totalAdmissions", searchAdmissionByUser.size());
        if (searchAdmissionByUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No Admission founded by id: " + id);
            return "redirect:/admin/home";
        }
        return "show_all_admissions";
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
    public String showAllAdmission(Model model) {
        List<AdminAdmission> listAdmission = this.adminAdmissionRepository.findAll();
        model.addAttribute("admissions", listAdmission);
        model.addAttribute("totalAdmissions", listAdmission.size());
        return "show_all_admissions";
    }

    // show all admission by user
    @GetMapping("/admin/customize/admission/all/byUser")
    public String showAllAdmissionByUser(Model model) {
        List<UserAdmission> listAdmissionByUser = this.userAdmissionRepository.findAll();
        model.addAttribute("admissions", listAdmissionByUser);
        model.addAttribute("totalAdmissions", listAdmissionByUser.size());
        return "show_all_admissions";
    }
}
