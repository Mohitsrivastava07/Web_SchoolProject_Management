package com.school.schoolproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "admin_contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "contact_name", nullable = false)
    @NotBlank(message = "Contact name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Contact name contains only alphabets"
    )
    private String contactName;

    @Column(name = "contact_email_id", unique = true, nullable = false)
    @Email(message = "Contact email id is required")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$",
            message = "Contact email id contains only alphabets"
    )
    private String contactEmailId;

    @Column(name = "contact_phone")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Contact phone number contains only number"
    )
    private String contactPhone;

    @Column(name = "contact_subject", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactSubject contactSubject;

    @Column(name = "contact_message")
    private String contactMessage;

    @Column(name = "contact_date")
    @Temporal(TemporalType.DATE)
    public Date contactDate = new Date();
}
