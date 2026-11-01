package com.school.schoolproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "admin_enquiry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEnquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "fist name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "first name contain only alphabets"
    )
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "last name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "last name contain only alphabets"
    )
    private String lastName;

    @Column(name = "email_id", unique = true, nullable = false)
    @Email(message = "Email Id is more required")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$",
            message = "Email Id contain when your email id have end with '@gmail.com'"
    )
    private String emailId;

    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Please enter the 10-digit phone number"
    )
    private String phoneNumber;

    @Column(name = "program", nullable = false)
    @Enumerated(EnumType.STRING)
    private Program program;

    @Column(name = "enquiry_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnquiryType enquiryType;

    @Column(name = "message")
    private String yourMessage;

    @Column(name = "enquiryDate")
    @Temporal(TemporalType.DATE)
    public Date submittedFormDate = new Date();
}
