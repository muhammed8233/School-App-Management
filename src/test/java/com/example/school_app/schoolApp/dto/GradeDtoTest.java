package com.example.school_app.schoolApp.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeDtoTest {
    GradeDto gradeDto = new GradeDto();

    @Test
    void testToVerifyStudentIdIsSet(){
        gradeDto.setStudentId(1L);
        assertEquals(1L, gradeDto.getStudentId());
    }

    @Test
    void testToVerifyCourseIdIsSet(){
        gradeDto.setCourseId(2L);
        assertEquals(2L, gradeDto.getCourseId());
    }

    @Test
    void testToVerifyStudentScoreIsSet(){
        gradeDto.setScore(20.0);
        assertEquals(20.0, gradeDto.getScore());
    }
}