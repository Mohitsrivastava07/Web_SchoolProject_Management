package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAddStudent;
import com.school.schoolproject.entities.Grade;
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

        List<AdminAddStudent> student =
                adminAddStudentRepository.findByRollNumber(studentRollNumber);
        if (student.isEmpty()) {
            model.addAttribute("studentFound", student.get(0));
            return "update_student";
        }

        redirectAttributes.addFlashAttribute(
                "error",
                "No student found with Roll Number : " + studentRollNumber);
        return "redirect:/admin/home";
    }

    //==================SEARCH STUDENT BY ROLL NUMBER=====================
    @GetMapping("/admin/customize/student/search")
    public String searchStudentByRollNumber(@RequestParam("searchStudent") int rollNumber,
                                                                               Model model, RedirectAttributes redirectAttributes) {
        List<AdminAddStudent> searchStudent = this.adminAddStudentRepository.findByRollNumber(rollNumber);
        model.addAttribute("students", searchStudent);
        model.addAttribute("totalStudents", searchStudent.size());
        if (searchStudent.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No Student found by roll number " + rollNumber);
            return "redirect:/admin/home";
        }
        return "show_all_students";
    }

    //==================SEARCH STUDENT BY GRADE=====================
    @GetMapping("/admin/customize/student/searchByGrade")
    public String searchStudentByGrade(@RequestParam("searchStudent") Grade grade,
                                                                      Model model, RedirectAttributes redirectAttributes) {
        List<AdminAddStudent> foundStudentByGrade = this.adminAddStudentRepository.findStudentByGrade(grade);
        model.addAttribute("students", foundStudentByGrade);
        model.addAttribute("totalStudents", foundStudentByGrade.size());

        if (foundStudentByGrade.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No Student found with " + grade);
            return "redirect:/admin/home";
        }
        return "show_all_students";
    }

    // ================= UPDATE STUDENT =================
    @PostMapping("/updateSubmitAddStudentForm")
    public ResponseEntity<String> submitUpdateStudentPage(@ModelAttribute AdminAddStudent form) {

        List<AdminAddStudent> student =
                adminAddStudentRepository.findByRollNumber(form.getRollNumber());
        if (student.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student not found with Roll Number : "
                            + form.getRollNumber());
        }

        AdminAddStudent existing = student.get(0);

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
    public String showAllStudents(Model model) {
        List<AdminAddStudent> listStudent = this.adminAddStudentRepository.findAll();
        model.addAttribute("students", listStudent);
        model.addAttribute("totalStudents", listStudent.size());
        return "show_all_students";
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