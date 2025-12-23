package com.example.school_app.schoolApp.dto;

import com.example.school_app.schoolApp.Enum.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceRecordDtoTest {
    AttendanceRecordDto recordDto = new AttendanceRecordDto();

    @Test
    void testToVerifyStudentIdIsSet(){
        recordDto.setStudentId(1L);
        assertEquals(1L, recordDto.getStudentId());
    }

    @Test
    void testToVerifyCourseIdIsSet(){
        recordDto.setCourseId(2L);
        assertEquals(2L,recordDto.getCourseId());
    }

    @Test
    void testToCheckIfDateIsSet(){
        recordDto.setDate(LocalDate.of(2025, Month.DECEMBER,12));
        assertEquals(LocalDate.of(2025, Month.DECEMBER,12), recordDto.getDate());
    }

    @Test
    void testToVerifyStudentAttendanceStatusIsPresentIsCount() {
        recordDto.setPresent(12L);
        assertEquals(12L, recordDto.getPresent());
    }
    @Test
    void testToVerifyStudentAttendanceStatusIsAbsentIsCount(){
        recordDto.setAbsent(2L);
        assertEquals(2L, recordDto.getAbsent());
    }

    @Test
    void testToCheckIfStudentAttendanceStatusIsSet(){
        recordDto.setStatus(Status.ABSENT);
        assertEquals(Status.ABSENT, recordDto.getStatus());

    }

}