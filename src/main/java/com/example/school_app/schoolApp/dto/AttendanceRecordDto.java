package com.example.school_app.schoolApp.dto;

import com.example.school_app.schoolApp.Enum.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRecordDto {
    private Long courseId;
    private Long studentId;
    private Long present;
    private Long absent;
    private Status status;
    private LocalDate date;

    @Override
    public String toString() {
        return "AttendanceRecordDto{" +
                "absent=" + absent +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", present=" + present +
                ", status=" + status +
                ", date=" + date +
                '}';
    }
}
