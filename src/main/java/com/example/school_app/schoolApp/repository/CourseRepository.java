package com.example.school_app.schoolApp.repository;

import com.example.school_app.schoolApp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseCode(String courseCode);



}
