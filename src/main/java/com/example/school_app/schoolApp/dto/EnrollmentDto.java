package com.example.school_app.schoolApp.dto;


public class EnrollmentDto {
    private Long enrollmentId;
    private Long studentId;
    private Long courseId;


    public EnrollmentDto(){}
    public EnrollmentDto(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
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


    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    @Override
    public String toString() {
        return "EnrollmentDto{" +
                "enrollmentId=" + enrollmentId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
