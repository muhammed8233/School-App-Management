package com.example.school_app.schoolApp.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentDtoTest {
    EnrollmentDto request = new EnrollmentDto();

    @Test
    void testToVerifyStudentIdIsSet(){
        request.setStudentId(1L);
        assertEquals(1L, request.getStudentId());
    }

    @Test
    void testToVerifyCourseIdIsSet(){
        request.setCourseId(2L);
        assertEquals(2L,request.getCourseId());
    }

}