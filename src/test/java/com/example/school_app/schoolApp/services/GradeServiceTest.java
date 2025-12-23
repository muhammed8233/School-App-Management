package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Assessment;
import com.example.school_app.schoolApp.repository.GradeRepository;
import com.example.school_app.schoolApp.dto.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GradeServiceTest {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentService enrollmentService;


    @BeforeEach
    void setup() {
        gradeRepository.deleteAll();
    }


    @Test
    void testToRecordStudentGradeInACourse() {
        List<StudentDto> savedStudent = studentService.saveAllStudents(List.of(new StudentDto("musa", "musa@gmail.com", "ss1")));
        List<CourseDto> savedCourse = courseService.saveAllCourses(List.of(new CourseDto("physics", "phy101")));

        List<EnrollmentDto> enrollmentRequests = List.of(
                new EnrollmentDto(savedStudent.get(0).getStudentId(), savedCourse.get(0).getCourseId()));

        List<EnrollmentDto> enrollmentDtoList = enrollmentService.saveAllEnrollments(enrollmentRequests);

        gradeService.recordStudentScore(enrollmentDtoList.get(0).getStudentId(),
                enrollmentDtoList.get(0).getCourseId(), Assessment.TEST,
                50.0);

        List<ScoreDto> grades = gradeService.getAllStudentScoreInACourse();

        assertNotNull(grades);
        assertEquals(1, grades.size());
        ScoreDto firstResult = grades.get(0);
        assertEquals(savedCourse.get(0).getCourseId(), firstResult.getCourseId());
        assertEquals(50.0, firstResult.getTestScore());

    }

    @Transactional
    @Test
    void testToGetAllStudentScoreInACourse(){
        List<StudentDto> savedStudents = studentService.saveAllStudents(List.of(new StudentDto("musa", "musa@gmail.com", "ss1")));
        List<CourseDto> savedCourses = courseService.saveAllCourses(List.of(new CourseDto("physics", "phy101")));
        List<EnrollmentDto> enrollments = enrollmentService.saveAllEnrollments(List.of(
                new EnrollmentDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId())));

        gradeService.saveAllGrades(List.of(
                new GradeDto(enrollments.get(0).getStudentId(), enrollments.get(0).getCourseId(), Assessment.TEST, 50.0),
                new GradeDto(enrollments.get(0).getStudentId(), enrollments.get(0).getCourseId(), Assessment.EXAM, 90.0),
                new GradeDto(enrollments.get(0).getStudentId(), enrollments.get(0).getCourseId(), Assessment.ASSIGNMENT, 20.0)
        ));
       List<ScoreDto>  result = gradeService.getAllStudentScoreInACourse();

       assertNotNull(result);
       assertEquals(1, result.size());
       assertEquals(74.0, result.get(0).getFinalScore());
    }

    @Transactional
    @Test
    void testToComputeFinalScoreForStudent() {

        List<StudentDto> savedStudents = studentService.saveAllStudents(List.of(new StudentDto("musa", "musa@gmail.com", "ss1")));
        List<CourseDto> savedCourses = courseService.saveAllCourses(List.of(new CourseDto("physics", "phy101")));
        List<EnrollmentDto> enrollments = enrollmentService.saveAllEnrollments(List.of(
                new EnrollmentDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId())));

        gradeService.saveAllGrades(List.of(
                new GradeDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId(), Assessment.TEST, 50.0),
                new GradeDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId(), Assessment.EXAM, 90.0)
        ));


        double result = gradeService.computeFinalScore(enrollments.get(0).getEnrollmentId());

        assertNotNull(result);
        assertEquals(74.0, result, 0.01);
    }


}