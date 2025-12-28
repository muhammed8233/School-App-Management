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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("test")
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


    @Test
    void testMarkStudentAttendance() throws IOException {
        LocalDate testDate = LocalDate.of(2025, 12, 18);
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());


        StudentDto musa = studentServiceImpl.saveAllStudents(List.of(
                StudentDto.builder()
                        .name("musa")
                        .email("musa@gmail.com").className("ss1")
                        .profileImage(image).build())).getFirst();

        CourseDto physics = courseServiceImpl.saveAllCourses(List.of(
                 CourseDto.builder()
                         .courseName("physics")
                         .courseCode("phy101").build())).getFirst();

        EnrollmentDto enrollment = new EnrollmentDto();
        enrollment.setCourseId(physics.getCourseId());
        enrollment.setStudentId(musa.getStudentId());

        enrollmentServiceImpl.saveAllEnrollments(List.of(enrollment));

        assertEquals(0, attendanceRecordRepository.findAll().size());
        attendanceRecordServiceImpl.markStudentAttendance(
                musa.getStudentId(), physics.getCourseId(), testDate, Status.PRESENT);
        assertEquals(1, attendanceRecordRepository.findAll().size());

        AttendanceRecordDto recordDto = attendanceRecordServiceImpl.getStudentAttendance(
                musa.getStudentId(), physics.getCourseId());

        assertNotNull(recordDto);
        assertEquals(1, recordDto.getPresent());
        assertEquals(physics.getCourseId(), recordDto.getCourseId());
        assertEquals(musa.getStudentId(), recordDto.getStudentId());
        assertEquals(testDate, recordDto.getDate());
    }

    @Test
    void testToSaveAllAttendanceRecords() throws IOException{
        LocalDate testDate = LocalDate.of(2025, 12, 21);
        LocalDate testDate1 = LocalDate.of(2025, 12, 22);
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());


        StudentDto savedStudents = studentServiceImpl.saveAllStudents(List.of(
                StudentDto.builder()
                        .name("abel")
                        .email("abel@gmail.com")
                        .className("ss1")
                        .profileImage(image)
                        .build())).getFirst();

        CourseDto savedCourses = courseServiceImpl.saveAllCourses(List.of(
                 CourseDto.builder()
                         .courseName("physics")
                         .courseCode("phy101")
                         .build())).getFirst();

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

        assertEquals(0, attendanceRecordRepository.findAll().size());
        List<AttendanceRecordDto> dto = attendanceRecordServiceImpl.
                saveAllAttendanceRecords(List.of(recordDto, recordDto1));
        assertEquals(2, attendanceRecordRepository.findAll().size());

        assertNotNull(dto);
        assertEquals(savedStudents.getStudentId(), dto.getFirst().getStudentId());
        assertEquals(Status.ABSENT, dto.getFirst().getStatus());
        assertEquals(Status.PRESENT, dto.getLast().getStatus());
        assertEquals(LocalDate.of(2025, 12, 21), dto.getFirst().getDate());
        assertEquals(savedStudents.getStudentId(), dto.getLast().getStudentId());
    }

    @Test
    void testToGetAllStudentAttendanceRecord() throws IOException {
        MultipartFile image = new MockMultipartFile("img",
                "img.jpg", "image/jpg", "data".getBytes());

        List<StudentDto> savedStudents = studentServiceImpl.saveAllStudents(List.of(StudentDto.builder()
                .name("musa")
                .email("musa@gmail.com")
                .className("ss1")
                .profileImage(image).build()));

        List<CourseDto> savedCourses = courseServiceImpl.saveAllCourses(List.of(CourseDto.builder()
                .courseName("physics")
                .courseCode("phy101")
                .build()));

        List<EnrollmentDto> enrollments = enrollmentServiceImpl.saveAllEnrollments(List.of(
                 EnrollmentDto.builder()
                         .studentId(savedStudents.getFirst().getStudentId())
                         .courseId(savedCourses.getFirst().getCourseId())
                         .build()));

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

        assertEquals(0, attendanceRecordRepository.findAll().size());
        attendanceRecordServiceImpl.saveAllAttendanceRecords(List.of(recordDto, recordDto1));
        assertEquals(2, attendanceRecordRepository.findAll().size());

        AttendanceRecordDto dto = attendanceRecordServiceImpl
                .getStudentAttendance(enrollments.getFirst().getStudentId(),
                enrollments.getFirst().getCourseId());

        assertNotNull(dto);
        assertEquals(1, dto.getPresent());
        assertEquals(1, dto.getAbsent());
    }
}