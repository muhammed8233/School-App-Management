package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Status;
import com.example.school_app.schoolApp.repository.AttendanceRecordRepository;
import com.example.school_app.schoolApp.dto.AttendanceRecordDto;
import com.example.school_app.schoolApp.dto.CourseDto;
import com.example.school_app.schoolApp.dto.EnrollmentDto;
import com.example.school_app.schoolApp.dto.StudentDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
class AttendanceRecordServiceImplTest {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private AttendanceRecordServiceImpl attendanceRecordServiceImpl;
    @Autowired
    private StudentServiceImpl studentServiceImpl;
    @Autowired
    private CourseServiceImpl courseServiceImpl;
    @Autowired
    private EnrollmentServiceImpl enrollmentServiceImpl;

    @BeforeEach
    void setup() {
        attendanceRecordRepository.deleteAll();
    }

    @Transactional
    @Test
    void testMarkStudentAttendance() {
        LocalDate testDate = LocalDate.of(2025, 12, 18);

        StudentDto musa = studentServiceImpl.saveAllStudents(List.of(
                new StudentDto("musa", "musa@gmail.com", "ss1")
        )).get(0);

        CourseDto physics = courseServiceImpl.saveAllCourses(List.of(
                new CourseDto("physics", "phy101")
        )).get(0);

        EnrollmentDto enrollment = new EnrollmentDto();
        enrollment.setCourseId(physics.getCourseId());
        enrollment.setStudentId(musa.getStudentId());

        enrollmentServiceImpl.saveAllEnrollments(List.of(enrollment));

        attendanceRecordServiceImpl.markStudentAttendance(
                musa.getStudentId(), physics.getCourseId(), testDate, Status.PRESENT);

        AttendanceRecordDto recordDto = attendanceRecordServiceImpl.getStudentAttendance(
                musa.getStudentId(), physics.getCourseId());

        assertNotNull(recordDto);
        assertEquals(Status.PRESENT, recordDto.getStatus());
        assertEquals(physics.getCourseId(), recordDto.getCourseId());
        assertEquals(musa.getStudentId(), recordDto.getStudentId());
        assertEquals(testDate, recordDto.getDate());
    }

    @Transactional
    @Test
    void testToSaveAllAttendanceRecords() {
        LocalDate testDate = LocalDate.of(2025, 12, 21);
        LocalDate testDate1 = LocalDate.of(2025, 12, 22);

        StudentDto savedStudents = studentServiceImpl.saveAllStudents(List.of(
                new StudentDto("abel", "abel@gmail.com", "ss1"))).get(0);

        CourseDto savedCourses = courseServiceImpl.saveAllCourses(List.of(
                new CourseDto("physics", "phy101"))).get(0);

        EnrollmentDto enrollmentDto = new EnrollmentDto();
        enrollmentDto.setStudentId(savedStudents.getStudentId());
        enrollmentDto.setCourseId(savedCourses.getCourseId());

        enrollmentServiceImpl.saveAllEnrollments(List.of(enrollmentDto));

        AttendanceRecordDto recordDto = new AttendanceRecordDto();
        recordDto.setStudentId(savedStudents.getStudentId());
        recordDto.setCourseId(savedCourses.getCourseId());
        recordDto.setDate(testDate);
        recordDto.setStatus(Status.ABSENT);

        AttendanceRecordDto recordDto1 = new AttendanceRecordDto();
        recordDto1.setStudentId(savedStudents.getStudentId());
        recordDto1.setCourseId(savedCourses.getCourseId());
        recordDto1.setDate(testDate1);
        recordDto1.setStatus(Status.PRESENT);

        List<AttendanceRecordDto> dto = attendanceRecordServiceImpl.saveAllAttendanceRecords(List.of(recordDto, recordDto1));

        assertNotNull(dto);
        assertEquals(2, dto.size());
        assertEquals(savedStudents.getStudentId(), dto.getFirst().getStudentId());
        assertEquals(Status.ABSENT, dto.getFirst().getStatus());
        assertEquals(Status.PRESENT, dto.getLast().getStatus());
        assertEquals(LocalDate.of(2025, 12, 21), dto.getFirst().getDate());
        assertEquals(savedStudents.getStudentId(), dto.getLast().getStudentId());
    }

    @Test
    void testToGetAllStudentAttendanceRecord() {
        List<StudentDto> savedStudents = studentServiceImpl.saveAllStudents(List.of(new StudentDto("musa", "musa@gmail.com", "ss1")));
        List<CourseDto> savedCourses = courseServiceImpl.saveAllCourses(List.of(new CourseDto("physics", "phy101")));
        List<EnrollmentDto> enrollments = enrollmentServiceImpl.saveAllEnrollments(List.of(
                new EnrollmentDto(savedStudents.get(0).getStudentId(), savedCourses.get(0).getCourseId())));

        AttendanceRecordDto recordDto = new AttendanceRecordDto();
        recordDto.setStudentId(enrollments.getFirst().getStudentId());
        recordDto.setCourseId(enrollments.getFirst().getCourseId());
        recordDto.setDate(LocalDate.of(2025, 12, 18));
        recordDto.setStatus(Status.ABSENT);

        AttendanceRecordDto recordDto1 = new AttendanceRecordDto();
        recordDto1.setStudentId(enrollments.getFirst().getStudentId());
        recordDto1.setCourseId(enrollments.getFirst().getCourseId());
        recordDto1.setDate(LocalDate.of(2025, 12, 19));
        recordDto1.setStatus(Status.PRESENT);

        attendanceRecordServiceImpl.saveAllAttendanceRecords(List.of(recordDto, recordDto1));
        AttendanceRecordDto dto = attendanceRecordServiceImpl.getStudentAttendance(enrollments.getFirst().getStudentId(),
                enrollments.getFirst().getCourseId());

        assertNotNull(dto);
        assertEquals(1, dto.getPresent());
        assertEquals(1, dto.getAbsent());
    }
}