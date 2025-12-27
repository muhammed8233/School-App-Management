package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.EnrollmentAlreadyExistException;
import com.example.school_app.schoolApp.exception.EnrollmentNotFoundException;
import com.example.school_app.schoolApp.model.Course;
import com.example.school_app.schoolApp.model.Enrollment;
import com.example.school_app.schoolApp.model.Student;
import com.example.school_app.schoolApp.repository.EnrollmentRepository;
import com.example.school_app.schoolApp.dto.EnrollmentDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private final StudentServiceImpl studentServiceImpl;
    @Autowired
    private final CourseServiceImpl courseServiceImpl;


    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, StudentServiceImpl studentServiceImpl,
                                 CourseServiceImpl courseServiceImpl){
        this.enrollmentRepository = enrollmentRepository;
        this.studentServiceImpl = studentServiceImpl;
        this.courseServiceImpl = courseServiceImpl;
    }

    @Override
    public EnrollmentDto enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentServiceImpl.getStudentById(studentId);

        Course course = courseServiceImpl.getCourseById(courseId);

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new EnrollmentAlreadyExistException("Student is already enrolled in this course");
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

    @Override
    public List<EnrollmentDto> saveAllEnrollments(List<EnrollmentDto> enrollmentDtoList) {
        if (enrollmentDtoList == null || enrollmentDtoList.isEmpty()) {
            throw new EnrollmentNotFoundException("Enrollment list cannot be empty");
        }

        List<Enrollment> enrollmentsToSave = new ArrayList<>();

        for (EnrollmentDto dto : enrollmentDtoList) {
            Student student = studentServiceImpl.getStudentById(dto.getStudentId());

            Course course = courseServiceImpl.getCourseById(dto.getCourseId());

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

    public Enrollment findEnrollmentByStudentAndCourse(Student student, Course course) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        if(enrollment == null){
            throw new EnrollmentNotFoundException("Enrollment not found");
        }
        return enrollment;
    }

    public Enrollment findByStudentIdAndCourseId(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
        if(enrollment == null){
            throw new EnrollmentAlreadyExistException("Enrollment not found");
        }
        return enrollment;
    }
}
