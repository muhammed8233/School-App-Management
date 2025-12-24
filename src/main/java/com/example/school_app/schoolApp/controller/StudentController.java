package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.StudentDto;
import com.example.school_app.schoolApp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
@Validated
public class StudentController {

    @Autowired private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentDto> register(@ModelAttribute @Valid StudentDto dto) throws IOException {
        return new ResponseEntity<>(studentService.addNewStudent(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudent(){
       List<StudentDto> savedStudent = studentService.getStudents();

        return ResponseEntity.ok(savedStudent);
    }

    @PostMapping("/bulk-register")
    public ResponseEntity<List<StudentDto>> registerMultipleStudents(@RequestBody List<StudentDto> dtos) {
        List<StudentDto> savedStudents = studentService.saveAllStudents(dtos);
        return new ResponseEntity<>(savedStudents, HttpStatus.CREATED);
    }


}
