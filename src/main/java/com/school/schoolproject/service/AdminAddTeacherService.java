package com.school.schoolproject.service;

import com.school.schoolproject.entities.AdminAddTeacher;
import com.school.schoolproject.repository.AdminAddTeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminAddTeacherService {

    @Autowired
    private AdminAddTeacherRepository repository;

    @Transactional
    public AdminAddTeacher saveTeacher(AdminAddTeacher teacher) {

        if (teacher.getEmployeeId() == null || teacher.getEmployeeId().isBlank()) {

            String nextId = generateEmployeeId();
            teacher.setEmployeeId(nextId);
        }

        return repository.save(teacher);
    }

    private String generateEmployeeId() {

        Optional<AdminAddTeacher> lastTeacher =
                repository.findTopByOrderByEmployeeIdDesc();

        if (lastTeacher.isEmpty()) {
            return "EMP001";
        }

        String lastId = lastTeacher.get().getEmployeeId();

        int number = Integer.parseInt(lastId.substring(3));

        return String.format("EMP%03d", number + 1);
    }
}
