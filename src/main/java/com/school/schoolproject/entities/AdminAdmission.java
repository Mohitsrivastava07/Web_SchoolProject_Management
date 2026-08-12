package com.school.schoolproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "admin_admission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAdmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_name", nullable = false)
    @NotBlank(message = "Student name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Student name contains only alphabets"
    )
    private String studentName;

    @Column(name = "date_of_birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "nationality", nullable = false)
    @NotBlank(message = "Nationality is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Nationality contains only alphabets"
    )
    private String nationality;

    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "academic_year", nullable = false)
    @Enumerated(EnumType.STRING)
    private AcademicYear academicYear;

    @Column(name = "parent_name", nullable = false)
    @NotBlank(message = "Parent name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Parent name contains only alphabets"
    )
    private String parentName;

    @Column(name = "relationship", nullable = false)
    @Enumerated(EnumType.STRING)
    private RelationShip relationShip;

    @Column(name = "parent_email", nullable = false)
    @Email(message = "Parent email id is required")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$",
            message = "Parent email id contains only alphabets"
    )
    private String parentEmailId;

    @Column(name = "parent_number", nullable = false)
    @NotBlank(message = "Parent phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Please enter the 10-digit parent phone number"
    )
    private String parentPhoneNumber;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Address is required")
    private String address;

    @Column(name = "previous_school", nullable = false)
    @NotBlank(message = "Previous school is required")
    private String previousSchool;

    @Column(name = "addition_message")
    @NotBlank(message = "Additional message is required")
    private String additionalMessage;

    @Column(name = "student_inserted_at", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date insertedAt = new Date();
}
