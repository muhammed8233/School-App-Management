package com.example.school_app.schoolApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long Id;
    @NotBlank(message = "course name must not be empty")
    @Size(min = 2, max = 30, message = "course name must not be > 2 or <= 30 ")
    private String courseName;

    @NotBlank(message = "course code must not be empty")
    @Size(min = 3, max = 10, message = "course code must not be > 3 or <= 10")
    private String courseCode;


    public void setCourseCode(String courseCode) {
        if(courseCode != null) {
            this.courseCode = courseCode.toLowerCase().trim();
        }else {
            this.courseCode = null;
        }
    }

    public void setCourseName(String courseName) {
        if (courseName != null) {
            this.courseName = courseName.toLowerCase().trim();
        }else {
            this.courseName = null;
        }

    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseId=" + Id +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
