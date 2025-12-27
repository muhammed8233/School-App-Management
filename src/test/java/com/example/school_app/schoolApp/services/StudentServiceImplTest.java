package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.StudentAlreadyExistException;
import com.example.school_app.schoolApp.repository.StudentRepository;
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
class StudentServiceImplTest {

    @Autowired
    private StudentServiceImpl studentServiceImpl;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ImageServiceImpl imageServiceImpl;

    @BeforeEach
    void setUp(){
        studentRepository.deleteAll();
    }


    @Test
    void testToVerifyRegisterStudent() throws IOException {
        MockMultipartFile image = new MockMultipartFile(
                "profileImage", "test.jpg", "image/jpeg", "test data".getBytes());

        StudentDto studentDto = StudentDto.builder()
                .name("bala")
                .email("bala@gmail.com").className("ss1")
                .profileImage(image).build();

        assertEquals(0,studentRepository.findAll().size());

        StudentDto result = studentServiceImpl.registerStudent(studentDto);
        assertEquals(1, studentRepository.findAll().size());

        assertNotNull(result);
        assertNotNull(result.getStudentId());
        assertEquals("bala@gmail.com", result.getEmail());

    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() throws IOException {
        MultipartFile image = new MockMultipartFile("img", "img.jpg", "image/jpg", "data".getBytes());

        StudentDto studentDto = StudentDto.builder()
                .name("bala")
                .email("bala@gmail.com").className("ss1")
                .profileImage(image).build();

        studentServiceImpl.registerStudent(studentDto);

        assertThrows(StudentAlreadyExistException.class, () -> {
            studentServiceImpl.registerStudent(studentDto);
        });
    }

    @Test
    void testToViewAllStudent() throws IOException{
        MultipartFile image = new MockMultipartFile("img", "img.jpg", "image/jpg", "data".getBytes());
        MultipartFile image1 = new MockMultipartFile("img", "img.jpg", "image/jpg", "data".getBytes());

        StudentDto student = StudentDto.builder()
                .name("musa")
                .email("musa@gmail.com").className("ss1")
                .profileImage(image).build();

        StudentDto student1 = StudentDto.builder()
                .name("bala")
                .email("bala@gmail.com").className("ss1")
                .profileImage(image1).build();

        assertEquals(0, studentRepository.findAll().size());
        studentServiceImpl.saveAllStudents(List.of(student1, student));
        assertEquals(2, studentRepository.findAll().size());

        List<StudentDto> result = studentServiceImpl.getStudents();

        assertNotNull(result);
        assertNotNull(result.get(1).getStudentId());

    }
}