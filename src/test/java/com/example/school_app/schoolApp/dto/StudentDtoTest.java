package com.example.school_app.schoolApp.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDtoTest {
    StudentDto studentDto = new StudentDto();

    @Test
    void testToVerifySetName(){
        studentDto.setName("safiya");
        assertEquals("safiya", studentDto.getName());
    }
    @Test
    void testToVerifySetEmail(){
        studentDto.setEmail("sule4@gmail.com");
        assertEquals("sule4@gmail.com", studentDto.getEmail());
    }
    @Test
    void testToVerifySetClassName(){
        studentDto.setClassName("ss1");
        assertEquals("ss1", studentDto.getClassName());
    }

}