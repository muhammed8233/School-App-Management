package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.StudentAlreadyExistException;
import com.example.school_app.schoolApp.exception.StudentNotFoundException;
import com.example.school_app.schoolApp.repository.StudentRepository;
import com.example.school_app.schoolApp.model.Student;
import com.example.school_app.schoolApp.dto.StudentDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final ImageServiceImpl imageServiceImpl;

    public StudentServiceImpl(StudentRepository studentRepository, ImageServiceImpl imageServiceImpl){
        this.studentRepository = studentRepository;
        this.imageServiceImpl = imageServiceImpl;
    }

    @Transactional
    @Override
    public StudentDto addNewStudent( StudentDto studentDto) throws IOException {
        boolean exists = studentRepository.existsByEmail(studentDto.getEmail());
        if(exists){
            throw new StudentAlreadyExistException("student with email:" + studentDto.getEmail() + " already exist");
        }

        String imageUrl = imageServiceImpl.uploadImage(studentDto.getProfileImage(), "student_profiles");

        Student student = new Student();
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setClassName(studentDto.getClassName());
        student.setProfileImageUrl(imageUrl);

         Student savedStudent = studentRepository.save(student);

         StudentDto dto = new StudentDto();
         dto.setStudentId(savedStudent.getId());
         dto.setName(savedStudent.getName());
         dto.setEmail(savedStudent.getEmail());
         dto.setClassName(savedStudent.getClassName());
         dto.setProfileImageUrl(savedStudent.getProfileImageUrl());

         return dto;
    }

    @Override
    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDto = new ArrayList<>();

        for (Student student : students){
            StudentDto studentDto1 = new StudentDto();
            studentDto1.setStudentId(student.getId());
            studentDto1.setName(student.getName());
            studentDto1.setEmail(student.getEmail());
            studentDto1.setClassName(student.getClassName());
            studentDto1.setProfileImageUrl(student.getProfileImageUrl());

            studentDto.add(studentDto1);
        }

        return studentDto;
    }

    @Transactional
    @Override
    public List<StudentDto> saveAllStudents(List<StudentDto> studentDto){
        if(studentDto == null || studentDto.isEmpty() ){
            throw new StudentNotFoundException("student dto can not be empty");
        }
        List<Student> students = new ArrayList<>();

        for (StudentDto dto : studentDto){
            if(studentRepository.existsByEmail(dto.getEmail())){
                throw new StudentAlreadyExistException("Email " + dto.getEmail() + " already exists");
            }
            Student student = new Student(dto.getName(), dto.getEmail(), dto.getClassName());
            students.add(student);
        }

        List<Student> savedStudents = studentRepository.saveAll(students);

        List<StudentDto> dtos = new ArrayList<>();
        for(Student student : savedStudents){
            StudentDto studentDto1 = new StudentDto();
            studentDto1.setStudentId(student.getId());
            studentDto1.setName(student.getName());
            studentDto1.setEmail(student.getEmail());
            studentDto1.setClassName(student.getClassName());
            dtos.add(studentDto1);
        }
        return dtos;
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with Id " + id + " not found"));
    }

}

