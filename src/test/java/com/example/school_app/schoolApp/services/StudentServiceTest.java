package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.StudentAlreadyExistException;
import com.example.school_app.schoolApp.repository.StudentRepository;
import com.example.school_app.schoolApp.dto.StudentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private  StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @MockitoBean
    private ImageService imageService;

    @BeforeEach
    void setUp(){
        studentRepository.deleteAll();
    }


    @Test
    void testToVerifyAddNewStudent() throws IOException {
        MockMultipartFile fakeFile = new MockMultipartFile(
                "profileImage", "test.jpg", "image/jpeg", "test data".getBytes());

        StudentDto studentDto = new StudentDto("bala", "bala@gmail.com", "ss1", fakeFile);

        org.mockito.BDDMockito.given(imageService.uploadImage(any(), anyString())).willReturn("cloudinary.com");

        StudentDto result = studentService.addNewStudent(studentDto);

        assertNotNull(result);
        assertEquals("bala@gmail.com", result.getEmail());
        assertEquals("cloudinary.com", result.getProfileImageUrl());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() throws IOException {
        MultipartFile image = new MockMultipartFile("img", "img.jpg", "image/jpg", "data".getBytes());
        org.mockito.BDDMockito.given(imageService.uploadImage(any(), anyString())).willReturn("url");

        StudentDto studentDto = new StudentDto("bala", "bala@gmail.com", "ss1", image);


        studentService.addNewStudent(studentDto);

        assertThrows(StudentAlreadyExistException.class, () -> {
            studentService.addNewStudent(studentDto);
        });
    }

    @Test
    void testToViewAllStudent(){
        StudentDto student = new StudentDto("musa","musa@hotmail.com","ss1");
        StudentDto student1 = new StudentDto("bala","bala@gmail.com","ss1");
        studentService.saveAllStudents(List.of(student1, student));

        List<StudentDto> result = studentService.getStudents();

        assertNotNull(result);
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        assertEquals(2,result.size());
        assertEquals("musa@hotmail.com", result.get(1).getEmail());
        assertEquals("bala", result.get(0).getName());
        assertEquals("musa", result.get(1).getName());

    }

}