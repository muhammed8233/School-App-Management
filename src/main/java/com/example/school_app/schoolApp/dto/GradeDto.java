package com.example.school_app.schoolApp.dto;

import com.example.school_app.schoolApp.Enum.Assessment;

public class GradeDto {
   private Long studentId;
   private Long courseId;
   private double score;
   private Assessment assessmentType;



    public GradeDto() {

    }

    public GradeDto(Long studentId, Long courseId, Assessment assessmentType, double score) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.assessmentType = assessmentType;
        this.score = score;

    }

    public Assessment getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(Assessment assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
