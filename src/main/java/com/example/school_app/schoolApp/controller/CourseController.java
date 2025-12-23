package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.CourseDto;
import com.example.school_app.schoolApp.services.CourseServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/course")
@Validated
public class CourseController {
    @Autowired
    private final CourseServiceInterface courseServiceInterface;


    public CourseController(CourseServiceInterface courseServiceInterface){
        this.courseServiceInterface = courseServiceInterface;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getStudentCourse(){
        List<CourseDto> saved = courseServiceInterface.getStudentCourse();
        return ResponseEntity.ok(saved);
    }

    @PostMapping
    public ResponseEntity<CourseDto> addNewCourse(@Valid @RequestBody CourseDto courseDto){
      CourseDto courseDto1 =  courseServiceInterface.addNewCourse(courseDto);
         return new ResponseEntity<>(courseDto1,HttpStatus.CREATED);
    }

    @PostMapping("/bulk-register")
    public ResponseEntity<List<CourseDto>> multipleRegister(@RequestBody List<CourseDto> courseDtoList){
        List<CourseDto> savedCourses = courseServiceInterface.saveAllCourses(courseDtoList);
        return new ResponseEntity<>(savedCourses, HttpStatus.CREATED);

    }
}
