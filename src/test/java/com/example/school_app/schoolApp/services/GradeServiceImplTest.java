package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Assessment;
import com.example.school_app.schoolApp.repository.GradeRepository;
import com.example.school_app.schoolApp.dto.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GradeServiceImplTest {

    @Autowired
    private GradeServiceImpl gradeServiceImpl;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentServiceImpl studentServiceImpl;
    @Autowired
    private CourseServiceImpl courseServiceImpl;
    @Autowired
    private EnrollmentServiceImpl enrollmentServiceImpl;


    @BeforeEach
    void setup() {
        gradeRepository.deleteAll();
    }


    @Test
    void testToRecordStudentGradeInACourse() {
        List<StudentDto> savedStudent = studentServiceImpl.saveAllStudents(List.of(new StudentDto("musa", "musa@gmail.com", "ss1")));
        List<CourseDto> savedCourse = courseServiceImpl.saveAllCourses(List.of(new CourseDto("physics", "phy101")));

        List<EnrollmentDto> enrollmentRequests = List.of(
                new EnrollmentDto(savedStudent.get(0).getStudentId(), savedCourse.get(0).getCourseId()));

        List<EnrollmentDto> enrollmentDtoList = enrollmentServiceImpl.saveAllEnrollments(enrollmentRequests);

        gradeServiceImpl.recordStudentScore(enrollmentDtoList.get(0).getStudentId(),
                enrollmentDtoList.get(0).getCourseId(), Assessment.TEST,
                50.0);

        ScoreDto grades = gradeServiceImpl.getStudentScoreInCourse(enrollmentDtoList.getFirst().getStudentId(), enrollmentDtoList.getFirst().getCourseId());

        assertNotNull(grades);
        assertEquals(savedCourse.get(0).getCourseId(), grades.getCourseId());
        assertEquals(50.0, grades.getTestScore());

    }

    @Transactional
    @Test
    void testToGetAllStudentScoreInACourse(){
        List<StudentDto> savedStudents = studentServiceImpl.saveAllStudents(List.of(new StudentDto("musa", "musa@gmail.com", "ss1")));
        List<CourseDto> savedCourses = courseServiceImpl.saveAllCourses(List.of(new CourseDto("physics", "phy101")));
        List<EnrollmentDto> enrollments = enrollmentServiceImpl.saveAllEnrollments(List.of(
                new EnrollmentDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId())));

        gradeServiceImpl.saveAllGrades(List.of(
                new GradeDto(enrollments.get(0).getStudentId(), enrollments.get(0).getCourseId(), Assessment.TEST, 50.0),
                new GradeDto(enrollments.get(0).getStudentId(), enrollments.get(0).getCourseId(), Assessment.EXAM, 90.0),
                new GradeDto(enrollments.get(0).getStudentId(), enrollments.get(0).getCourseId(), Assessment.ASSIGNMENT, 20.0)
        ));

       ScoreDto  result = gradeServiceImpl.getStudentScoreInCourse(enrollments.getFirst().getStudentId(), enrollments.getFirst().getCourseId());

       assertNotNull(result);
       assertEquals(74.0, result.getFinalScore());
    }

    @Transactional
    @Test
    void testToComputeFinalScoreForStudent() {

        List<StudentDto> savedStudents = studentServiceImpl.saveAllStudents(List.of(new StudentDto("musa", "musa@gmail.com", "ss1")));
        List<CourseDto> savedCourses = courseServiceImpl.saveAllCourses(List.of(new CourseDto("physics", "phy101")));
        List<EnrollmentDto> enrollments = enrollmentServiceImpl.saveAllEnrollments(List.of(
                new EnrollmentDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId())));

        gradeServiceImpl.saveAllGrades(List.of(
                new GradeDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId(), Assessment.TEST, 50.0),
                new GradeDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId(), Assessment.EXAM, 90.0)
        ));


        double result = gradeServiceImpl.computeFinalScore(enrollments.get(0).getEnrollmentId());

        assertNotNull(result);
        assertEquals(74.0, result, 0.01);
    }


}