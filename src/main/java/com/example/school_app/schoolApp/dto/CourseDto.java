package com.example.school_app.schoolApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseDto {
    private Long courseId;

    @NotBlank(message = "course name must not be empty")
    @Size(min = 2, max = 30, message = "course name must not be > 2 or <= 30 ")
    private String courseName;

    @NotBlank(message = "course code must not be empty")
    @Size(min = 3, max = 10, message = "course code must not be > 3 or <= 10")
    private String courseCode;

    public CourseDto(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public CourseDto() {

    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        if(courseCode != null) {
            this.courseCode = courseCode.toLowerCase().trim();
        }else {
            this.courseCode = null;
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        if (courseName != null) {
            this.courseName = courseName.toLowerCase().trim();
        }else {
            this.courseName = null;
        }

    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }
}
