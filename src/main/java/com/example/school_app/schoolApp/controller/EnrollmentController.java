package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.EnrollmentDto;
import com.example.school_app.schoolApp.services.EnrollmentService;
import com.example.school_app.schoolApp.services.GradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/enrollment")
@Validated
public class EnrollmentController {

    @Autowired private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService, GradeServiceImpl gradeServiceImpl){
        this.enrollmentService = enrollmentService;
    }


    @PostMapping
    public ResponseEntity<EnrollmentDto> enrollStudentInACourse(@RequestBody EnrollmentDto enrollmentDto){
       EnrollmentDto enrollmentDto1 = enrollmentService.enrollStudentInCourse(enrollmentDto.getStudentId(),
                enrollmentDto.getCourseId());
         return new ResponseEntity<>(enrollmentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentDto>> getAllStudentInACourse(@PathVariable Long courseId ) {
        List<EnrollmentDto> savedEnrollment = enrollmentService.getStudentsByACourse(courseId);

        return new ResponseEntity<>(savedEnrollment, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDto>> getAllCourseOfAStudent(@PathVariable Long studentId){
        List<EnrollmentDto> savedEnrollment = enrollmentService.getCourseByStudent(studentId);

        return new ResponseEntity<>(savedEnrollment, HttpStatus.CREATED);
    }

    @GetMapping("/getAllEnrollment")
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollment(){
        List<EnrollmentDto> savedEnrollment = enrollmentService.getAllEnrollment();

        return ResponseEntity.ok(savedEnrollment);
    }
}
