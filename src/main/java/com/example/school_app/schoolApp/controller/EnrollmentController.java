package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.EnrollmentDto;
import com.example.school_app.schoolApp.services.EnrollmentServiceInterface;
import com.example.school_app.schoolApp.services.GradeService;
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

    @Autowired private final EnrollmentServiceInterface enrollmentServiceInterface;

    public EnrollmentController(EnrollmentServiceInterface enrollmentServiceInterface, GradeService gradeService){
        this.enrollmentServiceInterface = enrollmentServiceInterface;
    }


    @PostMapping
    public ResponseEntity<EnrollmentDto> enrollStudentInACourse(@RequestBody EnrollmentDto enrollmentDto){
       EnrollmentDto enrollmentDto1 = enrollmentServiceInterface.enrollStudentInCourse(enrollmentDto.getStudentId(),
                enrollmentDto.getCourseId());
         return new ResponseEntity<>(enrollmentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentDto>> getAllStudentInACourse(@PathVariable Long courseId ) {
        List<EnrollmentDto> savedEnrollment = enrollmentServiceInterface.getStudentsByACourse(courseId);

        return new ResponseEntity<>(savedEnrollment, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDto>> getAllCourseOfAStudent(@PathVariable Long studentId){
        List<EnrollmentDto> savedEnrollment = enrollmentServiceInterface.getCourseByStudent(studentId);

        return new ResponseEntity<>(savedEnrollment, HttpStatus.CREATED);
    }

    @GetMapping("/getAllEnrollment")
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollment(){
        List<EnrollmentDto> savedEnrollment = enrollmentServiceInterface.getAllEnrollment();

        return ResponseEntity.ok(savedEnrollment);
    }
}
