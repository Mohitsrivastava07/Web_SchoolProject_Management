package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAddTeacher;
import com.school.schoolproject.entities.Department;
import com.school.schoolproject.entities.Designation;
import com.school.schoolproject.entities.Gender;
import com.school.schoolproject.handler.DetectContentTypeHandler;
import com.school.schoolproject.repository.AdminAddTeacherRepository;
import com.school.schoolproject.service.AdminAddTeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdminAddTeacherController {

    @Autowired
    private AdminAddTeacherService adminAddTeacherService;

    @Autowired
    private AdminAddTeacherRepository adminAddTeacherRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @PostMapping("/submitAddTeacherForm")
    public ResponseEntity<String> submitAddTeacherForm(@Valid @ModelAttribute AdminAddTeacher adminAddTeacher,
                                                       @RequestParam("qualificationCertificate")
                                                       MultipartFile certificate) throws Exception {
        if (!certificate.isEmpty()) {
            adminAddTeacher.setQualificationCertificate(certificate.getBytes());
        }

        adminAddTeacher.setEmployeeId(null);
        this.adminAddTeacherService.saveTeacher(adminAddTeacher);
        return ResponseEntity.status(HttpStatus
                .CREATED)
                .body("Teacher Form is submitted!");
    }

    // ================= OPEN UPDATE PAGE =================
    @GetMapping("/admin/customize/teacher/update")
    public String openStudentPage(
            @RequestParam("teacherId") String teacherId,
            Model model,
            RedirectAttributes redirectAttributes) {

        List<AdminAddTeacher> teacher =
                adminAddTeacherRepository.findByEmployeeId(teacherId);
        if (teacher.isEmpty()) {
            model.addAttribute("teacherFound", teacher.get(0));
            return "update_teacher";
        }

        redirectAttributes.addFlashAttribute(
                "error",
                "No student found with Roll Number : " + teacherId);
        return "redirect:/admin/home";
    }

    // ================= UPDATE TEACHER - FIXED =================
    @PostMapping("/updateSubmitAddTeacherForm")
    public ResponseEntity<String> updateTeacher(
            @RequestParam("employeeId") String employeeId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
            @RequestParam("gender") Gender gender,
            @RequestParam("nationality") String nationality,
            @RequestParam("department") Department department,
            @RequestParam("designation") Designation designation,
            @RequestParam("qualification") String qualification,
            @RequestParam(value = "qualificationCertificate", required = false) MultipartFile certificate,
            @RequestParam("experience") int experience,
            @RequestParam("emailId") String emailId,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address,
            @RequestParam("notes") String notes) throws IOException {

        List<AdminAddTeacher> optionalTeacher =
                adminAddTeacherRepository.findByEmployeeId(employeeId);

        if (optionalTeacher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Teacher not found!");
        }

        AdminAddTeacher teacher = optionalTeacher.get(0);

        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setDateOfBirth(dateOfBirth);
        teacher.setGender(gender);
        teacher.setNationality(nationality);
        teacher.setDepartment(department);
        teacher.setDesignation(designation);
        teacher.setQualification(qualification);
        teacher.setExperience(experience);
        teacher.setEmailId(emailId);
        teacher.setPhoneNumber(phoneNumber);
        teacher.setAddress(address);
        teacher.setNotes(notes);

        if (certificate != null && !certificate.isEmpty()) {
            teacher.setQualificationCertificate(certificate.getBytes());
        }

        adminAddTeacherRepository.save(teacher);

        return ResponseEntity.ok("Teacher updated successfully!");
    }

    @GetMapping("/admin/customize/teacher/search")
    public String searchTeacherByEmpId(@RequestParam("searchTeacher") String teacherEmpId,
                                                                          Model model, RedirectAttributes redirectAttributes) {
        List<AdminAddTeacher> searchTeacher = this.adminAddTeacherRepository.findByEmployeeId(teacherEmpId);
        model.addAttribute("teachers", searchTeacher);
        model.addAttribute("totalTeachers", searchTeacher.size());
        if (searchTeacher.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! No teacher founded with employee id " + teacherEmpId);
            return "redirect:/admin/home";
        }
        return "show_all_teachers";
    }

    @PostMapping("/admin/customize/teacher/delete")
    public ResponseEntity<String> deleteTeacherByEmpId(@RequestParam("teacherId") String employeeId,
                                                       Model model) {
        int affectedRows = this.adminAddTeacherRepository.deleteByEmpId(employeeId);
        if (affectedRows > 0) {
            return ResponseEntity.ok("Teacher deleted successfully! " + affectedRows + " row affected.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher Not Found!");
    }

    @GetMapping("/admin/customize/teacher/all")
    public String showAllTeacher(Model model) {
        List<AdminAddTeacher> listTeacher = this.adminAddTeacherRepository.findAll();
        model.addAttribute("teachers", listTeacher);
        model.addAttribute("totalTeachers", listTeacher.size());
        return "show_all_teachers";
    }

    //API to count the total add teacher
    @GetMapping("/api/teacher/count")
    @ResponseBody
    public Map<String, Long> getTeacherCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", adminAddTeacherRepository.count());
        return response;
    }
}