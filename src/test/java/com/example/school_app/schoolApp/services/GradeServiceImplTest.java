package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Assessment;
import com.example.school_app.schoolApp.repository.GradeRepository;
import com.example.school_app.schoolApp.dto.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    void testToRecordStudentGradeInACourse() throws IOException {
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());

        List<StudentDto> savedStudent = studentServiceImpl.saveAllStudents(List.of(StudentDto.builder()
                .name("musa")
                .email("musa@gmail.com").className("ss1")
                .profileImage(image).build()));

        List<CourseDto> savedCourse = courseServiceImpl.saveAllCourses(List.of( CourseDto.builder()
                .courseName("physics")
                .courseCode("phy101").build()));

        List<EnrollmentDto> enrollmentRequests = List.of(
                EnrollmentDto.builder()
                        .studentId(savedStudent.getFirst().getStudentId())
                        .courseId(savedCourse.getFirst().getCourseId())
                        .build());

        List<EnrollmentDto> enrollmentDtoList = enrollmentServiceImpl.saveAllEnrollments(enrollmentRequests);

        gradeServiceImpl.recordStudentScore(enrollmentDtoList.getFirst().getStudentId(),
                enrollmentDtoList.getFirst().getCourseId(), Assessment.TEST,
                50.0);

        ScoreDto grades = gradeServiceImpl.getStudentScoreInCourse(
                enrollmentDtoList.getFirst().getStudentId(), enrollmentDtoList.getFirst().getCourseId());

        assertNotNull(grades);
        assertEquals(savedCourse.getFirst().getCourseId(), grades.getCourseId());
        assertEquals(50.0, grades.getTestScore());

    }

    @Test
    void testToGetAllStudentScoreInACourse() throws IOException{
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());

        List<StudentDto> savedStudents = studentServiceImpl.saveAllStudents(List.of(  StudentDto.builder()
                .name("musa")
                .email("musa@gmail.com").className("ss1")
                .profileImage(image).build()));

        List<CourseDto> savedCourses = courseServiceImpl.saveAllCourses(List.of( CourseDto.builder()
                .courseName("physics")
                .courseCode("phy101").build()));

        List<EnrollmentDto> enrollments = enrollmentServiceImpl.saveAllEnrollments(List.of(
                EnrollmentDto.builder()
                        .studentId(savedStudents.getFirst().getStudentId())
                        .courseId(savedCourses.getFirst().getCourseId())
                        .build()));

        assertEquals(0,gradeRepository.findAll().size());
        gradeServiceImpl.saveAllGrades(List.of(
                 GradeDto.builder()
                         .studentId(enrollments.getFirst().getStudentId())
                         .courseId(enrollments.getFirst().getCourseId())
                         .assessmentType(Assessment.TEST)
                         .score(50.0).build(),
                GradeDto.builder()
                        .studentId(enrollments.getFirst().getStudentId())
                        .courseId(enrollments.getFirst().getCourseId())
                        .assessmentType(Assessment.EXAM)
                        .score(90.0).build(),
                GradeDto.builder()
                        .studentId(enrollments.getFirst().getStudentId())
                        .courseId(enrollments.getFirst().getCourseId())
                        .assessmentType(Assessment.ASSIGNMENT)
                        .score(20.0).build()
        ));
        assertEquals(3, gradeRepository.findAll().size());
       ScoreDto  result = gradeServiceImpl.getStudentScoreInCourse(
               enrollments.getFirst().getStudentId(), enrollments.getFirst().getCourseId());

       assertNotNull(result);
       assertEquals(74.0, result.getFinalScore());
    }

    @Test
    void testToComputeFinalScoreForStudent() throws IOException{
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());

        List<StudentDto> savedStudents = studentServiceImpl.saveAllStudents(List.of(  StudentDto.builder()
                .name("musa")
                .email("musa@gmail.com").className("ss1")
                .profileImage(image).build()));

        List<CourseDto> savedCourses = courseServiceImpl.saveAllCourses(List.of( CourseDto.builder()
                .courseName("physics")
                .courseCode("phy101").build()));

        List<EnrollmentDto> enrollments = enrollmentServiceImpl.saveAllEnrollments(List.of(
                EnrollmentDto.builder()
                        .studentId(savedStudents.getFirst().getStudentId())
                        .courseId(savedCourses.getFirst().getCourseId())
                        .build()));

        assertEquals(0, gradeRepository.findAll().size());
        gradeServiceImpl.saveAllGrades(List.of(
                GradeDto.builder()
                        .studentId(enrollments.getFirst().getStudentId())
                        .courseId(enrollments.getFirst().getCourseId())
                        .assessmentType(Assessment.TEST)
                        .score(50.0).build(),
                GradeDto.builder()
                        .studentId(enrollments.getFirst().getStudentId())
                        .courseId(enrollments.getFirst().getCourseId())
                        .assessmentType(Assessment.EXAM)
                        .score(90.0).build()
        ));
        assertEquals(2, gradeRepository.findAll().size());

        double result = gradeServiceImpl.computeFinalScore(enrollments.getFirst().getEnrollmentId());

        assertEquals(74.0, result, 0.01);
    }
}