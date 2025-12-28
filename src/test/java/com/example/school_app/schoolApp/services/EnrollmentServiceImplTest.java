package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.repository.EnrollmentRepository;
import com.example.school_app.schoolApp.dto.CourseDto;
import com.example.school_app.schoolApp.dto.EnrollmentDto;
import com.example.school_app.schoolApp.dto.StudentDto;
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
class EnrollmentServiceImplTest {
    @Autowired private GradeServiceImpl gradeServiceImpl;
    @Autowired private StudentServiceImpl studentServiceImpl;
    @Autowired private EnrollmentServiceImpl enrollmentServiceImpl;
    @Autowired private CourseServiceImpl courseServiceImpl;
    @Autowired private EnrollmentRepository enrollmentRepository;

    @BeforeEach
    void setup(){
        enrollmentRepository.deleteAll();
    }

    @Test
    void testToEnrollStudentInACourse() throws IOException {
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());

        StudentDto savedStudent = studentServiceImpl.saveAllStudents(List.of(StudentDto.builder()
                .name("musa")
                .email("musa@gmail.com").className("ss1")
                .profileImage(image).build())).getFirst();

        CourseDto savedCourse = courseServiceImpl.saveAllCourses(List.of(CourseDto.builder()
                .courseName("physics")
                .courseCode("phy101").build())).getFirst();

        assertEquals(0, enrollmentRepository.findAll().size());
        enrollmentServiceImpl.enrollStudentInCourse(savedStudent.getStudentId(), savedCourse.getCourseId());
        assertEquals(1, enrollmentRepository.findAll().size());

        List<EnrollmentDto> enrollments = enrollmentServiceImpl.getAllEnrollment();

        assertNotNull(enrollments);
        assertEquals(1, enrollments.size());
        assertEquals(savedStudent.getStudentId(), enrollments.getFirst().getStudentId());
        assertEquals(savedCourse.getCourseId(), enrollments.getFirst().getCourseId());
    }

    @Test
    void testToGetAllCourseForAStudent() throws IOException {
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());
        MultipartFile image1 = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());

        List<StudentDto> savedStudents = studentServiceImpl.saveAllStudents(List.of(
                StudentDto.builder()
                        .name("musa")
                        .email("musa@gmail.com").className("ss1")
                        .profileImage(image).build(),
                StudentDto.builder()
                        .name("yahaya")
                        .email("yahaya@gmail.com").className("ss1")
                        .profileImage(image1).build()));

        List<CourseDto> savedCourses = courseServiceImpl.saveAllCourses(List.of(
                        CourseDto.builder()
                                .courseName("physics")
                                .courseCode("phy101").build(),
                        CourseDto.builder()
                                .courseName("chemistry")
                                .courseCode("chm101").build()));


        Long student1Id = savedStudents.getFirst().getStudentId();
        Long course1Id = savedCourses.get(0).getCourseId();
        Long course2Id = savedCourses.get(1).getCourseId();


        EnrollmentDto enrollmentDto1 = new EnrollmentDto();
        enrollmentDto1.setCourseId(course1Id);
        enrollmentDto1.setStudentId(student1Id);

        EnrollmentDto enrollmentDto = new EnrollmentDto();
        enrollmentDto.setCourseId(course2Id);
        enrollmentDto.setStudentId(student1Id);

        assertEquals(0, enrollmentRepository.findAll().size());
        enrollmentServiceImpl.saveAllEnrollments(List.of(enrollmentDto1, enrollmentDto));
        assertEquals(2, enrollmentRepository.findAll().size());

        List<EnrollmentDto> result = enrollmentServiceImpl.getCourseByStudent(student1Id);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(course1Id, result.get(0).getCourseId());
        assertEquals(course2Id, result.get(1).getCourseId());

    }

     @Test
     void testToGetAllEnrollment() throws IOException{
         MultipartFile image = new MockMultipartFile("img",
                 "img.jpg", "image/jpg", "data".getBytes());
         MultipartFile image1 = new MockMultipartFile("img",
                 "img.jpg", "image/jpg", "data".getBytes());

         List <StudentDto> savedStudent = studentServiceImpl.saveAllStudents(List.of(
                 StudentDto.builder()
                         .name("musa")
                         .email("musa@gmail.com").className("ss1")
                         .profileImage(image).build(),
                 StudentDto.builder()
                         .name("yahaya")
                         .email("yahaya@gmail.com").className("ss1")
                         .profileImage(image1).build()));

         List<CourseDto> savedCourse = courseServiceImpl.saveAllCourses(List.of(
                 CourseDto.builder()
                         .courseName("physics")
                         .courseCode("phy101").build(),
                 CourseDto.builder()
                         .courseName("physics")
                         .courseCode("phy101").build()));


         EnrollmentDto enrollmentDto = new EnrollmentDto();
         enrollmentDto.setCourseId(savedCourse.getFirst().getCourseId());
         enrollmentDto.setStudentId(savedStudent.get(0).getStudentId());

         EnrollmentDto enrollmentDto1 = new EnrollmentDto();
         enrollmentDto1.setStudentId(savedStudent.get(1).getStudentId());
         enrollmentDto1.setCourseId(savedCourse.get(1).getCourseId());

         assertEquals(0, enrollmentRepository.findAll().size());
         enrollmentServiceImpl.saveAllEnrollments(List.of(enrollmentDto, enrollmentDto1));
         assertEquals(2, enrollmentRepository.findAll().size());
         List<EnrollmentDto> result = enrollmentServiceImpl.getAllEnrollment();

         assertNotNull(result);
         assertEquals(2, result.size());
     }
     @Test
     void testToGetAllStudentByACourse() throws IOException{
         MultipartFile image = new MockMultipartFile("img",
                 "img.jpg", "image/jpg", "data".getBytes());
         MultipartFile image1 = new MockMultipartFile("img",
                 "img.jpg", "image/jpg", "data".getBytes());

         StudentDto studentDto = studentServiceImpl.saveAllStudents(List.of(StudentDto.builder()
                 .name("musa")
                 .email("musa@gmail.com").className("ss1")
                 .profileImage(image).build())).getFirst();

         CourseDto courseDto = courseServiceImpl.saveAllCourses(List.of( CourseDto.builder()
                 .courseName("physics")
                 .courseCode("phy101").build())).getFirst();

         StudentDto studentDto2 = studentServiceImpl.saveAllStudents(List.of(StudentDto.builder()
                 .name("yahaya")
                 .email("yahaya@gmail.com").className("ss1")
                 .profileImage(image1).build())).getFirst();

         EnrollmentDto enrollment = new EnrollmentDto();
         enrollment.setCourseId(courseDto.getCourseId());
         enrollment.setStudentId(studentDto.getStudentId());

         EnrollmentDto enrollment1 = new EnrollmentDto();
         enrollment1.setStudentId(studentDto2.getStudentId());
         enrollment1.setCourseId(courseDto.getCourseId());

         assertEquals(0, enrollmentRepository.findAll().size());
         enrollmentServiceImpl.saveAllEnrollments(List.of(enrollment, enrollment1));
         assertEquals(2, enrollmentRepository.findAll().size());

         List<EnrollmentDto> result = enrollmentServiceImpl.getStudentsByACourse(courseDto.getCourseId());

         assertNotNull(result);
         System.out.println(result);
         assertEquals(2, result.size());
         assertEquals(studentDto.getStudentId(), result.getFirst().getStudentId());
     }
}
