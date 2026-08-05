package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAddStudent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AllFormController {

    //----------------------------------------- admin dashboard --------------------------------------------------
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "dashboard";
    }

    //---------------------------------------------user panel-----------------------------------------------------
    @GetMapping("/user/home")
    public String userHome() {
        return "user_home";
    }

    @GetMapping("/user/academic")
    public String userAcademic() {
        return "user_academic";
    }

    @GetMapping("/user/campus")
    public String userCampus() {
        return "user_campus";
    }

    @GetMapping("/user/contact")
    public String userContact() {
        return "user_contact";
    }

    @GetMapping("/user/admission")
    public String userAdmission() {
        return "user_admission";
    }

    @GetMapping("/user/enquiry")
    public String userEnquiry() {
        return "user_enquiry";
    }

    //----------------------------------user-campus-backened-------------------------------------------------------
    @GetMapping("/user/campus/building")
    public String userCampusBuilding() {
        return "user_campus_building";
    }

    @GetMapping("/user/campus/library")
    public String userCampusLibrary() {
        return "user_campus_library";
    }

    @GetMapping("/user/campus/sport_field")
    public String userCampusSportField() {
        return "user_campus_sport_field";
    }

    @GetMapping("/user/campus/science_lab")
    public String userCampusScienceLab() {
        return "user_campus_science_lab";
    }

    @GetMapping("/user/campus/computer_lab")
    public String userCampusComputerLab() {
        return "user_campus_computer_lab";
    }

    //------------------------------------------admin panel----------------------------------------------------
    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin_home";
    }

    @GetMapping("/admin/academic")
    public String adminAcademic() {
        return "admin_academic";
    }

    @GetMapping("/admin/campus")
    public String adminCampus() {
        return "admin_campus";
    }

    @GetMapping("/admin/addTeacher")
    public String adminAddTeacher() {
        return "admin_addTeacher";
    }

    @GetMapping("/admin/addStudent")
    public String adminAddStudent() {
        return "admin_addStudent";
    }

    @GetMapping("/admin/contact")
    public String adminContact() {
        return "admin_contact";
    }

    @GetMapping("/admin/admission")
    public String adminAdmission() {
        return "admin_admission";
    }

    @GetMapping("/admin/enquiry")
    public String adminEnquiry() {
        return "admin_enquiry";
    }

    //----------------------------------admini-campus-backened-------------------------------------------------------
    @GetMapping("/admin/campus/building")
    public String adminCampusBuilding() {
        return "admin_campus_building";
    }

    @GetMapping("/admin/campus/library")
    public String adminCampusLibrary() {
        return "admin_campus_library";
    }

    @GetMapping("/admin/campus/sport_field")
    public String adminCampusSportField() {
        return "admin_campus_sport_field";
    }

    @GetMapping("/admin/campus/science_lab")
    public String adminCampusScienceLab() {
        return "admin_campus_science_lab";
    }

    @GetMapping("/admin/campus/computer_lab")
    public String adminCampusComputerLab() {
        return "admin_campus_computer_lab";
    }


    //-------------------------------------login-registration panel------------------------------------------------
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
