package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.CourseNotFoundException;
import com.example.school_app.schoolApp.repository.CourseRepository;
import com.example.school_app.schoolApp.dto.CourseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
class CourseServiceTest {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp(){
        courseRepository.deleteAll();
    }


    @Test
    void testToVerifyCourse(){
        CourseDto courseDto = new CourseDto("physic","phy101");
         courseService.addNewCourse(courseDto);
         List<CourseDto> courses = courseService.getAllCoursesAsDto();


        assertNotNull(courses);
        assertEquals("physic", courses.getFirst().getCourseName());
        assertEquals("phy101", courses.getFirst().getCourseCode());

    }

    @Test
    void testViewAllCourse(){
        CourseDto course = new CourseDto("chemistry","chm101");
        CourseDto course1 = new CourseDto("physics","phy101");
        CourseDto course2 = new CourseDto( "biology","bio101");
        courseService.saveAllCourses(List.of(course, course1, course2));

        List<CourseDto> result = courseService.getStudentCourse();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("chemistry", result.get(0).getCourseName());
        assertEquals("physics", result.get(1).getCourseName());
        assertEquals("phy101", result.get(1).getCourseCode());

    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        CourseDto invalidDto = new CourseDto(" ", "phy101");

        assertThrows(CourseNotFoundException.class, () -> {courseService.addNewCourse(invalidDto);});
    }


}