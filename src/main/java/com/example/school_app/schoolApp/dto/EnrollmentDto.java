package com.example.school_app.schoolApp.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentDto {
    private Long enrollmentId;
    private Long studentId;
    private Long courseId;

    @Override
    public String toString() {
        return "EnrollmentDto{" +
                "enrollmentId=" + enrollmentId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
