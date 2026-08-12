package com.school.schoolproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "admin_marksheet_data")
@NoArgsConstructor
@AllArgsConstructor
public class AdminMarksheetData {

    @Id
    @Column(name = "roll_number", nullable = false)
    private int rollNumber;

    @Column(name = "student_name", nullable = false)
    @NotBlank(message = "Student name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Student name contains only alphabets"
    )
    private String studentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @OneToMany(mappedBy = "adminMarksheetData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Marksheet> marksheets = new ArrayList<>();
}
