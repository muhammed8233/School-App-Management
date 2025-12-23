package com.example.school_app.schoolApp.model;

import com.example.school_app.schoolApp.Enum.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AttendanceRecord {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "enrollment_Id",
            nullable = false
    )
    private Enrollment enrollment;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Status status;
    public AttendanceRecord(){}
    public AttendanceRecord(Long id, Enrollment enrollmentId, Status status, LocalDate date) {
        this.id = id;
        this.enrollment = enrollmentId;
        this.status = status;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "id=" + id +
                ", date=" + date +
                ", status=" + status +
                ", enrollmentId=" + (enrollment != null ? enrollment.getId() : "null") +
                '}';
    }

}
