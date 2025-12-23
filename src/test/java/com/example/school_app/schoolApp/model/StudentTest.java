package com.example.school_app.schoolApp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student = new Student();


    @Test
    void VerifySetName() {
        student.setName("abubakar");
        assertEquals("abubakar",student.getName());
    }
    @Test
    void verifySetEmail(){
        student.setEmail("abubakar@gmail.com");
        assertEquals("abubakar@gmail.com", student.getEmail());
    }

    @Test
    void testToVerifySetId(){
        student.setId(1L);
        assertEquals(1L, student.getId());
    }
    @Test
    void testToVerifyClassName(){
        student.setClassName("physic");
        assertEquals("physic", student.getClassName());
    }
}