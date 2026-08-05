package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAddStudent;
import com.school.schoolproject.repository.AdminAddStudentRepository;
import com.school.schoolproject.service.AdminAddStudentService;
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
public class AdminAddStudentController {

    @Autowired
    private AdminAddStudentService adminAddStudentService;

    @Autowired
    private AdminAddStudentRepository adminAddStudentRepository;

    // ================= ADD STUDENT =================
    @PostMapping("/submitAddStudentForm")
    public ResponseEntity<String> submitAddStudent(
            @Valid @ModelAttribute AdminAddStudent adminAddStudent) {

        boolean submit = adminAddStudentService.saveAddStudent(adminAddStudent);
        if (!submit) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Student insertion failed!");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Student inserted successfully!");
    }

    // ================= OPEN UPDATE PAGE =================
    @GetMapping("/admin/customize/student/update")
    public String openStudentPage(
            @RequestParam("studentRollNumber") int studentRollNumber,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<AdminAddStudent> student =
                adminAddStudentRepository.findByRollNumber(studentRollNumber);
        if (student.isPresent()) {
            model.addAttribute("studentFound", student.get());
            return "update_student";
        }

        redirectAttributes.addFlashAttribute(
                "error",
                "No student found with Roll Number : " + studentRollNumber);
        return "redirect:/admin/home";
    }

    //==================SEARCH STUDENT=====================
    @GetMapping("/admin/customize/student/search")
    public ResponseEntity<Optional<AdminAddStudent>> searchStudentByRollNumber(@RequestParam("searchStudent") int rollNumber,
                                                                               Model model) {
        Optional<AdminAddStudent> searchStudent = this.adminAddStudentRepository.findByRollNumber(rollNumber);
        if (searchStudent != null) {
            return ResponseEntity.ok(searchStudent);
        }
        return ResponseEntity.notFound().build();
    }

    // ================= UPDATE STUDENT =================
    @PostMapping("/updateSubmitAddStudentForm")
    public ResponseEntity<String> submitUpdateStudentPage(@ModelAttribute AdminAddStudent form) {

        Optional<AdminAddStudent> student =
                adminAddStudentRepository.findByRollNumber(form.getRollNumber());
        if (student.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student not found with Roll Number : "
                            + form.getRollNumber());
        }

        AdminAddStudent existing = student.get();

        existing.setFirstName(form.getFirstName());
        existing.setLastName(form.getLastName());
        existing.setDateOfBirth(form.getDateOfBirth());
        existing.setGender(form.getGender());
        existing.setGrade(form.getGrade());
        existing.setSection(form.getSection());
        existing.setAdmissionFormSubmitDate(form.getAdmissionFormSubmitDate());
        existing.setParentName(form.getParentName());
        existing.setRelationShip(form.getRelationShip());
        existing.setParentEmailId(form.getParentEmailId());
        existing.setParentPhone(form.getParentPhone());
        existing.setAddress(form.getAddress());
        existing.setNotes(form.getNotes());

        boolean updated = adminAddStudentService.saveAddStudent(existing);
        if (!updated) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Student update failed!");
        }
        return ResponseEntity.ok("Student updated successfully!");
    }

    @PostMapping("/admin/customize/student/delete")
    public ResponseEntity<String> deleteStudentByRollNumber(@RequestParam("deleteStudent") int rollNumber,
                                                            Model model) {
       int affectedRows = this.adminAddStudentRepository.deleteByRollNumber(rollNumber);
       if (affectedRows > 0) {
           return ResponseEntity.ok("Student deleted successfully! " + affectedRows + " row affected.");
       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student Not Found!");
    }

    @GetMapping("/admin/customize/student/all")
    public ResponseEntity<List<AdminAddStudent>> showAllStudents(Model model) {
        List<AdminAddStudent> listStudent = this.adminAddStudentRepository.findAll();
        return ResponseEntity.ok(listStudent);
    }

    //API to count the total add student
    @GetMapping("/api/student/count")
    @ResponseBody
    public Map<String, Long> getStudentCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", adminAddStudentRepository.count());
        return response;
    }
}