package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.StudentDto;
import com.example.school_app.schoolApp.services.StudentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
@Validated
public class StudentController {

    @Autowired private final StudentServiceInterface studentServiceInterface;

    public StudentController(StudentServiceInterface studentServiceInterface){
        this.studentServiceInterface = studentServiceInterface;
    }


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentDto> register(@ModelAttribute @Valid StudentDto dto) throws IOException {
        return new ResponseEntity<>(studentServiceInterface.addNewStudent(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudent(){
       List<StudentDto> savedStudent = studentServiceInterface.getStudents();

        return ResponseEntity.ok(savedStudent);
    }

    @PostMapping("/bulk-register")
    public ResponseEntity<List<StudentDto>> registerMultipleStudents(@RequestBody List<StudentDto> dtos) {
        List<StudentDto> savedStudents = studentServiceInterface.saveAllStudents(dtos);
        return new ResponseEntity<>(savedStudents, HttpStatus.CREATED);
    }


}
