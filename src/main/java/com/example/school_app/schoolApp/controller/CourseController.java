package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.CourseDto;
import com.example.school_app.schoolApp.services.CourseService;
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
    private final CourseService courseService;


    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getStudentCourse(){
        List<CourseDto> saved = courseService.getStudentCourse();
        return ResponseEntity.ok(saved);
    }

    @PostMapping
    public ResponseEntity<CourseDto> addNewCourse(@Valid @RequestBody CourseDto courseDto){
      CourseDto courseDto1 =  courseService.addNewCourse(courseDto);
         return new ResponseEntity<>(courseDto1,HttpStatus.CREATED);
    }

    @PostMapping("/bulk-register")
    public ResponseEntity<List<CourseDto>> multipleRegister(@RequestBody List<CourseDto> courseDtoList){
        List<CourseDto> savedCourses = courseService.saveAllCourses(courseDtoList);
        return new ResponseEntity<>(savedCourses, HttpStatus.CREATED);

    }
}
