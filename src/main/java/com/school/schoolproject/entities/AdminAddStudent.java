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
@Table(name = "admin_add_student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddStudent {

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "first name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "First name contains only alphabets"
    )
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "last name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Last name contains only alphabets"
    )
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "section", nullable = false)
    @Enumerated(EnumType.STRING)
    private Section section;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rollNumber;

    @Column(name = "admission_form_submit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionFormSubmitDate;

    @Column(name = "parent_name", nullable = false)
    @NotBlank(message = "parent name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Parent name contains only alphabets"
    )
    private String parentName;

    @Column(name = "relationship", nullable = false)
    @Enumerated(EnumType.STRING)
    private RelationShip relationShip;

    @Column(name = "parent_email", unique = true)
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
    private String parentPhone;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "address is required")
    private String address;

    @Column(name = "notes", nullable = false)
    @NotBlank(message = "notes is required")
    private String notes;

    @Column(name = "joining_date", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date joiningDate = new Date();
}
