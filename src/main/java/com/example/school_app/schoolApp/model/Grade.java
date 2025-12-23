package com.example.school_app.schoolApp.model;

import com.example.school_app.schoolApp.Enum.Assessment;
import jakarta.persistence.*;

@Entity
public class Grade {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Enumerated(EnumType.STRING)
    private Assessment assessmentType;
    private double score;

    public Grade(Enrollment enrollment, Assessment assessmentType, double score) {
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.score = score;
    }

    public Grade() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public com.example.school_app.schoolApp.Enum.Assessment getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(com.example.school_app.schoolApp.Enum.Assessment assessmentType) {
        this.assessmentType = assessmentType;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;

    }

    @Override
    public String toString() {
        return "Grade{" +
                "assessmentType=" + assessmentType +
                ", id=" + id +
                ", enrollmentId=" + enrollment +
                ", score=" + score +
                '}';
    }
}
