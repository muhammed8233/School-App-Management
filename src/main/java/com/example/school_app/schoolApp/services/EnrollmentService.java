package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.CourseNotFoundException;
import com.example.school_app.schoolApp.exception.StudentNotFoundException;
import com.example.school_app.schoolApp.model.Course;
import com.example.school_app.schoolApp.model.Enrollment;
import com.example.school_app.schoolApp.model.Student;
import com.example.school_app.schoolApp.repository.CourseRepository;
import com.example.school_app.schoolApp.repository.EnrollmentRepository;
import com.example.school_app.schoolApp.repository.StudentRepository;
import com.example.school_app.schoolApp.dto.EnrollmentDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService implements EnrollmentServiceInterface{

    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final CourseRepository courseRepository;


      public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository,
                       CourseRepository courseRepository){
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    @Override
    public EnrollmentDto enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + studentId + " not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        EnrollmentDto responseDto = new EnrollmentDto();
        responseDto.setEnrollmentId(savedEnrollment.getId());
        responseDto.setStudentId(savedEnrollment.getStudent().getId());
        responseDto.setCourseId(savedEnrollment.getCourse().getId());

        return responseDto;
    }

    @Override
    public List<EnrollmentDto> getCourseByStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            EnrollmentDto dto = new EnrollmentDto();
            dto.setEnrollmentId(enrollment.getId());
            dto.setStudentId(enrollment.getStudent().getId());
            dto.setCourseId(enrollment.getCourse().getId());

            enrollmentDtos.add(dto);
        }
        return enrollmentDtos;

    }

    @Override
    public Enrollment findEnrollmentByStudentAndCourse(Student student, Course course) {
        return enrollmentRepository.findByStudentAndCourse(student, course);
    }

    @Override
    public List<EnrollmentDto> getStudentsByACourse(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            EnrollmentDto dto = new EnrollmentDto();
            dto.setEnrollmentId(enrollment.getId());
            dto.setStudentId(enrollment.getStudent().getId());
            dto.setCourseId(enrollment.getCourse().getId());

            enrollmentDtos.add(dto);
        }
        return enrollmentDtos;
    }

    @Override
    public List<EnrollmentDto> getAllEnrollment() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        List<EnrollmentDto> enrollmentDto = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            EnrollmentDto dto = new EnrollmentDto();
            dto.setEnrollmentId(enrollment.getId());
            dto.setStudentId(enrollment.getStudent().getId());
            dto.setCourseId(enrollment.getCourse().getId());
            enrollmentDto.add(dto);
        }
        return enrollmentDto;
    }

    @Transactional
    @Override
    public List<EnrollmentDto> saveAllEnrollments(List<EnrollmentDto> enrollmentDtoList) {
        if (enrollmentDtoList == null || enrollmentDtoList.isEmpty()) {
            throw new IllegalStateException("Enrollment list cannot be empty");
        }

        List<Enrollment> enrollmentsToSave = new ArrayList<>();

        for (EnrollmentDto dto : enrollmentDtoList) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new IllegalStateException("Student " + dto.getStudentId() + " not found"));
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalStateException("Course " + dto.getCourseId() + " not found"));

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollmentsToSave.add(enrollment);
        }

        List<Enrollment> savedRecords = enrollmentRepository.saveAll(enrollmentsToSave);

        List<EnrollmentDto> dtos = new ArrayList<>();
        for (Enrollment record : savedRecords) {
            EnrollmentDto responseDto = new EnrollmentDto();
            responseDto.setEnrollmentId(record.getId());
            responseDto.setStudentId(record.getStudent().getId());
            responseDto.setCourseId(record.getCourse().getId());
            dtos.add(responseDto);
        }

        return dtos;
    }
}
