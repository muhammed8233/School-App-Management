package com.example.school_app.schoolApp.dto;

import com.example.school_app.schoolApp.Enum.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AttendanceRecordDto {
    private Long courseId;
    private Long studentId;
    private Long present;
    private Long absent;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Status status;
    private LocalDate date;

    public AttendanceRecordDto( Long present, Long absent) {
        this.present = present;
        this.absent = absent;
    }

    public AttendanceRecordDto() {

    }

    public AttendanceRecordDto(Long studentId, Long courseId, Long present, Long absent) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.present = present;
        this.absent = absent;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAbsent() {
        return absent;
    }

    public void setAbsent(Long absent) {
        this.absent = absent;
    }

    public Long getPresent() {
        return present;
    }

    public void setPresent(Long present) {
        this.present = present;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
