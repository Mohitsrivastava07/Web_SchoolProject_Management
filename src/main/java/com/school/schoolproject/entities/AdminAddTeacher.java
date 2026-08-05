package com.school.schoolproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "admin_add_teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddTeacher {

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

    @Column(name = "nationality", nullable = false)
    @NotBlank(message = "nationality is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Nationality contains only alphabets"
    )
    private String nationality;

    @Id
    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(name = "designation", nullable = false)
    @Enumerated(EnumType.STRING)
    private Designation designation;

    @Column(name = "qualification", nullable = false)
    @NotBlank(message = "qualification is required")
    @Pattern(
            regexp = "^[A-Za-z0-9\\s.,()'\\-]+$",
            message = "Qualification should contain only letters, numbers, spaces, dots, commas, parentheses, apostrophes, and hyphens"
    )
    private String qualification;

    @JdbcTypeCode(SqlTypes.VARBINARY)
    @Column(name = "certificate", columnDefinition = "bytea")
    private byte[] qualificationCertificate;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience must be 0 or greater")
    @Max(value = 20, message = "Experience seems too high")
    private int experience;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Please enter the 10-digit phone number"
    )
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "address is required")
    private String address;

    @Column(name = "notes", nullable = false)
    @NotBlank(message = "notes is required")
    private String notes;

    @Column(name = "joining_date")
    @Temporal(TemporalType.DATE)
    public Date joiningDate = new Date();
}
