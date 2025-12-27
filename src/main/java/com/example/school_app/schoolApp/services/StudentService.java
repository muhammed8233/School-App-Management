package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.model.Student;
import com.example.school_app.schoolApp.dto.StudentDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

public interface StudentService { ;

    StudentDto registerStudent(@Valid  StudentDto studentDTO) throws IOException;

    List<StudentDto> getStudents();

    List<StudentDto> saveAllStudents(List<StudentDto> studentDto) throws IOException;

    Student getStudentById(Long studentId);

}

