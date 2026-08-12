package com.school.schoolproject.controller;

import com.school.schoolproject.entities.*;
import com.school.schoolproject.repository.AdminMarksheetDataRepository;
import com.school.schoolproject.repository.MarksheetRepository;
import com.school.schoolproject.service.AdminMarksheetDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class AdminMarksheetDataController {

    @Autowired
    private AdminMarksheetDataService adminMarksheetDataService;

    @Autowired
    private AdminMarksheetDataRepository adminMarksheetDataRepository;

    @Autowired
    private MarksheetRepository marksheetRepository;

    @PostMapping("/submitStudentMarksheetData")
    public ResponseEntity<String> saveStudentMarksheetData(@RequestParam("studentName") String studentName,
                                                           @RequestParam("rollNumber") int rollNumber,
                                                           @RequestParam("grade") Grade grade,
                                                           @RequestParam("emailId") String emailId,
                                                           @RequestParam(value = "marksheet1") MultipartFile marksheet1,
                                                           @RequestParam(value = "marksheet2") MultipartFile marksheet2,
                                                           @RequestParam(value = "marksheet3") MultipartFile marksheet3) throws Exception {
        try {
            AdminMarksheetData adminMarksheetData = new AdminMarksheetData();
            adminMarksheetData.setStudentName(studentName);
            adminMarksheetData.setRollNumber(rollNumber);
            adminMarksheetData.setGrade(grade);
            adminMarksheetData.setEmailId(emailId);

            Marksheet m1 = new Marksheet();
            m1.setMarksheetType("1st_MarkSheet");
            m1.setFileName(marksheet1.getOriginalFilename());
            m1.setContentType(marksheet1.getContentType());
            m1.setFileData(marksheet1.getBytes());
            m1.setAdminMarksheetData(adminMarksheetData);

            Marksheet m2 = new Marksheet();
            m2.setMarksheetType("2nd_MarkSheet");
            m2.setFileName(marksheet2.getOriginalFilename());
            m2.setContentType(marksheet2.getContentType());
            m2.setFileData(marksheet2.getBytes());
            m2.setAdminMarksheetData(adminMarksheetData);

            Marksheet m3 = new Marksheet();
            m3.setMarksheetType("3rd_MarkSheet");
            m3.setFileName(marksheet3.getOriginalFilename());
            m3.setContentType(marksheet3.getContentType());
            m3.setFileData(marksheet3.getBytes());
            m3.setAdminMarksheetData(adminMarksheetData);

            adminMarksheetData.getMarksheets().add(m1);
            adminMarksheetData.getMarksheets().add(m2);
            adminMarksheetData.getMarksheets().add(m3);

            boolean submit = this.adminMarksheetDataService.saveStudentMarksheetData(adminMarksheetData);
            if (!submit) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student marksheet data not successfully inserted!");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Student marksheet data successfully inserted!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save marksheet data: " + e.getMessage());
        }
    }

    @GetMapping("/marksheet/view/student_details")
    public String showAllEnquiryData(Model model, RedirectAttributes redirectAttributes) {
        List<Object[]> listMarksheets = this.marksheetRepository.findAllMarksheetData();
        model.addAttribute("marksheets", listMarksheets);
        model.addAttribute("totalMarksheets", listMarksheets != null ? listMarksheets.size() : 0);
        if (listMarksheets.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error! Not Marksheet Student data founded");
            return "redirect:/admin/home";
        }
        return "show_marksheet_details";
    }

    // viewing the marksheet of 1st
    @GetMapping("/marksheet/view")
    public ResponseEntity<Object> viewStudentMarksheets1(@RequestParam("studentName") String studentName,
                                                        @RequestParam("rollNumber") int rollNumber,
                                                        @RequestParam("emailId") String emailId) {

        AdminMarksheetData data = this.adminMarksheetDataRepository.findByStudentNameAndRollNumberAndEmailId(studentName, rollNumber, emailId)
                 .orElseThrow(() -> new RuntimeException("Student data (like name, roll, email) are founding!"));

        List<Marksheet> marksheets = data.getMarksheets();
        if (marksheets.isEmpty() || marksheets == null) {
            return ResponseEntity.notFound().build();
        }
        Marksheet marksheet1 = marksheets.get(0);

        String filename1 = data.getStudentName() + "_" + data.getRollNumber() + "_" + marksheet1.getFileName();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename1 + "\"")
                .contentType(MediaType.parseMediaType(marksheet1.getContentType()))
                .body(marksheet1.getFileData());
    }

    //viewing the marksheet 2nd
    @GetMapping("/marksheet2nd/view")
    public ResponseEntity<Object> viewStudentMarksheet2(@RequestParam("studentName") String studentName,
                                                        @RequestParam("rollNumber") int rollNumber,
                                                        @RequestParam("emailId") String emailId) {

        AdminMarksheetData data = this.adminMarksheetDataRepository.findByStudentNameAndRollNumberAndEmailId(studentName, rollNumber, emailId)
                .orElseThrow(() -> new RuntimeException("Student data (like name, roll, email) are not founding!"));

        List<Marksheet> marksheets = data.getMarksheets();
        if (marksheets.isEmpty() || marksheets == null) {
            return ResponseEntity.notFound().build();
        }

        Marksheet marksheet2 = marksheets.get(1);

        String filename2 = data.getStudentName() + "_" + data.getRollNumber() + "_" + marksheet2.getFileName();
        return
                ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename2 + "\"")
                        .contentType(MediaType.parseMediaType(marksheet2.getContentType()))
                        .body(marksheet2.getFileData());
    }

    // viewing the marksheet of 3rd
    @GetMapping("/marksheet3rd/view")
    public ResponseEntity<Object> viewStudentMarksheets3(@RequestParam("studentName") String studentName,
                                                         @RequestParam("rollNumber") int rollNumber,
                                                         @RequestParam("emailId") String emailId) {

        AdminMarksheetData data = this.adminMarksheetDataRepository.findByStudentNameAndRollNumberAndEmailId(studentName, rollNumber, emailId)
                .orElseThrow(() -> new RuntimeException("Student data (like name, roll, email) are founding!"));

        List<Marksheet> marksheets = data.getMarksheets();
        if (marksheets.isEmpty() || marksheets == null) {
            return ResponseEntity.notFound().build();
        }
        Marksheet marksheet3 = marksheets.get(2);

        String filename3 = data.getStudentName() + "_" + data.getRollNumber() + "_" + marksheet3.getFileName();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename3 + "\"")
                .contentType(MediaType.parseMediaType(marksheet3.getContentType()))
                .body(marksheet3.getFileData());
    }

    // download all marksheets
    @GetMapping("/marksheet/download")
    public ResponseEntity<Object> downloadStudentMarksheets(@RequestParam("studentName") String studentName,
                                                        @RequestParam("rollNumber") int rollNumber,
                                                        @RequestParam("emailId") String emailId) {

        AdminMarksheetData data = this.adminMarksheetDataRepository.findByStudentNameAndRollNumberAndEmailId(studentName, rollNumber, emailId)
                .orElseThrow(() -> new RuntimeException("Student data (like name, roll, email) are founding!"));

        List<Marksheet> marksheets = data.getMarksheets();
        if (marksheets.isEmpty() || marksheets == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            //getting the 1st marksheet out of 3 marksheets
            //Marksheet marksheet = marksheets.get(0);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

           for (Marksheet marksheet : marksheets) {
               String fileName = data.getStudentName() + "_" + data.getRollNumber() + "_" + marksheet.getFileName();
               ZipEntry entry = new ZipEntry(fileName);
               zos.putNextEntry(entry);
               zos.write(marksheet.getFileData());
               zos.closeEntry();
           }

           zos.close();
           baos.close();

            String zipFileName = data.getStudentName() + "_" + data.getRollNumber() + "_marksheets.zip";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating zip file: " + e.getMessage());
        }

    }
}
