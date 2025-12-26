package com.example.school_app.schoolApp.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {
    private Long courseId;
    private String courseName;
    private String courseCode;

    @Override
    public String toString() {
        return "CourseDto{" +
                "courseCode='" + courseCode + '\'' +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
