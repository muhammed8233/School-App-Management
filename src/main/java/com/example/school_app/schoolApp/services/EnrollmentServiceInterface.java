package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.model.Course;
import com.example.school_app.schoolApp.model.Enrollment;
import com.example.school_app.schoolApp.model.Student;
import com.example.school_app.schoolApp.dto.EnrollmentDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface EnrollmentServiceInterface {

    List<EnrollmentDto> getAllEnrollment();

    EnrollmentDto enrollStudentInCourse(Long studentId, Long courseId);

    List<EnrollmentDto> getStudentsByACourse(Long courseId);

    @Transactional
    List<EnrollmentDto> saveAllEnrollments(List<EnrollmentDto> enrollmentDto);


    List<EnrollmentDto> getCourseByStudent(Long studentId);

    Enrollment findEnrollmentByStudentAndCourse(Student student, Course course);
}
