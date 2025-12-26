package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.CourseNotFoundException;
import com.example.school_app.schoolApp.repository.CourseRepository;
import com.example.school_app.schoolApp.dto.CourseDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CourseServiceImplTest {

    @Autowired
    private CourseServiceImpl courseServiceImpl;
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp(){
        courseRepository.deleteAll();
    }


    @Test
    void testToVerifyCourse(){
        CourseDto courseDto = CourseDto.builder()
                .courseName("physics")
                .courseCode("phy101").build();

        assertEquals(0, courseRepository.findAll().size());
         courseServiceImpl.addNewCourse(courseDto);
         assertEquals(1, courseRepository.findAll().size());
         List<CourseDto> courses = courseServiceImpl.getAllCoursesAsDto();


        assertNotNull(courses);
        assertEquals("physics", courses.getFirst().getCourseName());
        assertEquals("phy101", courses.getFirst().getCourseCode());

    }

    @Test
    void testViewAllCourse(){
        CourseDto course = CourseDto.builder()
                .courseName("chemistry")
                .courseCode("chm01").build();
        CourseDto course1 = CourseDto.builder()
                .courseName("physics")
                .courseCode("phy101").build();
        CourseDto course2 = CourseDto.builder()
                .courseName("Biology ")
                .courseCode("bio101").build();

        assertEquals(0, courseRepository.findAll().size());
        courseServiceImpl.saveAllCourses(List.of(course, course1, course2));
        assertEquals(3, courseRepository.findAll().size());

        List<CourseDto> result = courseServiceImpl.getStudentCourse();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("chemistry", result.get(0).getCourseName());
        assertEquals("physics", result.get(1).getCourseName());
        assertEquals("phy101", result.get(1).getCourseCode());

    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        CourseDto invalidDto = CourseDto.builder()
                .courseName(" ")
                .courseCode("phy101").build();

        assertThrows(CourseNotFoundException.class, () -> {
            courseServiceImpl.addNewCourse(invalidDto);});
    }


}