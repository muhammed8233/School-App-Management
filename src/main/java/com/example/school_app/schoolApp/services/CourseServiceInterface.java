package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.model.Course;
import com.example.school_app.schoolApp.dto.CourseDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CourseServiceInterface {
    List<CourseDto> getStudentCourse();

    CourseDto addNewCourse(CourseDto courseDto);

    @Transactional
    List<CourseDto> saveAllCourses(List<CourseDto> courseDto);

    Course getCourseById(Long courseId);

    List<CourseDto> getAllCoursesAsDto();
}

