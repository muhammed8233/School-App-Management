package com.example.school_app.schoolApp.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course course = new Course(1L,"physics","phy101");

    @Test
    void testToVerifyCourseDetail(){
        assertEquals(1L, course.getId());
        assertEquals("physics", course.getCourseName());
        assertEquals("phy101", course.getCourseCode());
    }
}