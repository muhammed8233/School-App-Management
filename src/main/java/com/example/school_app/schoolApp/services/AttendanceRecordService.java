package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.EnrollmentNotFoundException;
import com.example.school_app.schoolApp.model.AttendanceRecord;
import com.example.school_app.schoolApp.model.Course;
import com.example.school_app.schoolApp.model.Enrollment;
import com.example.school_app.schoolApp.Enum.Status;
import com.example.school_app.schoolApp.repository.AttendanceRecordRepository;
import com.example.school_app.schoolApp.repository.CourseRepository;
import com.example.school_app.schoolApp.repository.EnrollmentRepository;
import com.example.school_app.schoolApp.repository.StudentRepository;
import com.example.school_app.schoolApp.model.Student;
import com.example.school_app.schoolApp.dto.AttendanceRecordDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class AttendanceRecordService implements AttendanceRecordServiceInterface {

    @Autowired
    private final AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    public AttendanceRecordService(AttendanceRecordRepository attendanceRecordRepository, StudentRepository studentRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public AttendanceRecordDto getStudentAttendance(Long studentId, Long courseId) {
        Long present = attendanceRecordRepository.countByEnrollmentStudentIdAndEnrollmentCourseIdAndStatus(
                studentId, courseId, Status.PRESENT);

        Long absent = attendanceRecordRepository.countByEnrollmentStudentIdAndEnrollmentCourseIdAndStatus(
                studentId, courseId, Status.ABSENT);

        AttendanceRecord lastRecord = attendanceRecordRepository
                .findTopByEnrollmentStudentIdAndEnrollmentCourseIdOrderByDateDesc(studentId, courseId);


        AttendanceRecordDto dto = new AttendanceRecordDto();
        dto.setStudentId(studentId);
        dto.setCourseId(courseId);
        dto.setAbsent(absent);
        dto.setPresent(present);

        if (lastRecord != null) {
            dto.setDate(lastRecord.getDate());
            dto.setStatus(lastRecord.getStatus());
        }

        return dto;
    }


    @Override
    public AttendanceRecordDto markStudentAttendance(Long studentId, Long courseId, LocalDate date, Status status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new RuntimeException("Course not found"));

       Enrollment  enrollment = enrollmentRepository.findByStudentAndCourse(student, course);

        if (attendanceRecordRepository.existsByEnrollmentAndDate(enrollment, date)) {
            throw new IllegalStateException("attendance for "+ enrollment.getStudent().getName()+
                    " has been already marked today");
        }
        AttendanceRecord attendance = new AttendanceRecord();
        attendance.setEnrollment(enrollment);
        attendance.setDate(date);
        attendance.setStatus(status);

       AttendanceRecord records = attendanceRecordRepository.save(attendance);

       AttendanceRecordDto recordDto = new AttendanceRecordDto();
       recordDto.setStudentId(records.getEnrollment().getStudent().getId());
       recordDto.setCourseId(records.getEnrollment().getCourse().getId());
       recordDto.setDate(records.getDate());
       recordDto.setStatus(records.getStatus());

       return recordDto;
    }

    @Transactional
    @Override
    public List<AttendanceRecordDto> saveAllAttendanceRecords(List<AttendanceRecordDto> attendanceRecordDtoList) {
        if (attendanceRecordDtoList == null || attendanceRecordDtoList.isEmpty()) {
            throw new RuntimeException("Attendance record cannot be empty");
        }
        List<AttendanceRecord> records = new ArrayList<>();

        for (AttendanceRecordDto dto : attendanceRecordDtoList) {

            Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(dto.getStudentId(), dto.getCourseId());
            if (enrollment == null) {
                throw new EnrollmentNotFoundException("No enrollment found for Student ID " +
                        dto.getStudentId() + " and Course ID " + dto.getCourseId());
            }
            AttendanceRecord record = new AttendanceRecord();
            record.setEnrollment(enrollment);
            record.setDate(dto.getDate());
            record.setStatus(dto.getStatus());

            records.add(record);
        }
        List<AttendanceRecord> dto = attendanceRecordRepository.saveAll(records);
        List<AttendanceRecordDto> dtos = new ArrayList<>();

        for (AttendanceRecord record : dto){
            AttendanceRecordDto recordDto = new AttendanceRecordDto();
            recordDto.setStudentId(record.getEnrollment().getStudent().getId());
            recordDto.setCourseId(record.getEnrollment().getCourse().getId());
            recordDto.setDate(record.getDate());
            recordDto.setStatus(record.getStatus());
            dtos.add(recordDto);
        }
        return dtos;
    }

}
