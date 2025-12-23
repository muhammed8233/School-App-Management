package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Status;
import com.example.school_app.schoolApp.dto.AttendanceRecordDto;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRecordServiceInterface {
    AttendanceRecordDto getStudentAttendance(Long studentId, Long courseId);
    AttendanceRecordDto markStudentAttendance(Long studentId, Long courseId, LocalDate date, Status status);
    List<AttendanceRecordDto> saveAllAttendanceRecords(List<AttendanceRecordDto> attendanceRecordDtoList);
}
