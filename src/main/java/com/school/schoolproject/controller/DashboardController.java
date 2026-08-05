package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAddStudent;
import com.school.schoolproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private AdminAddStudentRepository adminAddStudentRepository;

    @Autowired
    private AdminAddTeacherRepository adminAddTeacherRepository;

    @Autowired
    private UserAdmissionRepository userAdmissionRepository;

    @Autowired
    private UserContactRepository userContactRepository;

    @Autowired
    private UserEnquiryRepository userEnquiryRepository;

    // --------------------Add Student Dashboard------------------------------
    @GetMapping("/admin/checkStudentDashboard/byAdmin")
    public String checkStudentDashboard(Model model) {
        long studentCount = adminAddStudentRepository.count();
        model.addAttribute("studentCount", studentCount);
        return "dashboard";
    }

    // --------------------Add Teacher Dashboard------------------------------
    @GetMapping("/admin/checkTeacherDashboard/byAdmin")
    public String checkTeacherDashboard(Model model) {
        long teacherCount = adminAddTeacherRepository.count();
        model.addAttribute("teacherCount", teacherCount);
        return "dashboard";
    }

    // --------------------Admission Dashboard------------------------------
    @GetMapping("/admin/checkAdmissionDashboard/byUser")
    public String checkAdmissionDashboard(Model model) {
        long admissionCount = userAdmissionRepository.count();
        model.addAttribute("admissionCount", admissionCount);
        return "dashboard";
    }

    // --------------------Contact Dashboard------------------------------
    @GetMapping("/admin/checkContactDashboard/byUser")
    public String checkContactDashboard(Model model) {
        long contactCount = userContactRepository.count();
        model.addAttribute("contactCount", contactCount);
        return "dashboard";
    }

    // --------------------Enquiry Dashboard------------------------------
    @GetMapping("/admin/checkEnquiryDashboard/byUser")
    public String checkEnquiryDashboard(Model model) {
        long enquiryCount = userEnquiryRepository.count();
        model.addAttribute("enquiryCount", enquiryCount);
        return "dashboard";
    }
}
