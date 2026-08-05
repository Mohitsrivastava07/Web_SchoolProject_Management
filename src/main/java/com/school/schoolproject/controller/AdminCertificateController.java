package com.school.schoolproject.controller;

import com.school.schoolproject.entities.AdminAddTeacher;
import com.school.schoolproject.handler.DetectContentTypeHandler;
import com.school.schoolproject.repository.AdminAddTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AdminCertificateController {

    @Autowired
    private AdminAddTeacherRepository adminAddTeacherRepository;

    // ---- VIEW CERTIFICATE -----
    @GetMapping("/certificate/view")
    public ResponseEntity<Object> viewCertificate(@RequestParam("employeeId") String employeeId) throws Exception {
        Optional<AdminAddTeacher> teacherFound = Optional.of(this.adminAddTeacherRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new Exception("Teacher ID not found!")));

        byte[] certificate = teacherFound.get().getQualificationCertificate();
        if (certificate == null || certificate.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String contentType = DetectContentTypeHandler.detectContentType(certificate);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + teacherFound.get().getFirstName() + teacherFound.get().getEmployeeId() + "\"certificate")
                .contentType(MediaType.parseMediaType(contentType))
                .body(certificate);
    }

    @GetMapping("/certificate/download")
    public ResponseEntity<Object> downloadCertificate(@RequestParam("employeeId") String employeeId) throws Exception {
        Optional<AdminAddTeacher> teacherFound = Optional.of(this.adminAddTeacherRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new Exception("Teacher ID not found!")));

        byte[] certificate = teacherFound.get().getQualificationCertificate();
        if (certificate == null || certificate.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String contentType = DetectContentTypeHandler.detectContentType(certificate);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + teacherFound.get().getFirstName() + teacherFound.get().getEmployeeId() + "\"_certificate.pdf")
                .contentType(MediaType.parseMediaType(contentType))
                .body(certificate);
    }
}
