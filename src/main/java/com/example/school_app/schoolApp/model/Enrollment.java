package com.example.school_app.schoolApp.model;

import jakarta.persistence.*;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "student_id"
    )
    private Student student;

    @ManyToOne
    @JoinColumn(
            name = "course_id"
    )
    private Course course;


    public Enrollment(){}

    public Enrollment(Long id, Student student, Course course ) {
        this.id = id;
        this.student = student;
        this.course = course;

    }
    public Enrollment(Student student, Course course){
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "course=" + course +
                ", id=" + id +
                ", student=" + student +
                '}';
    }
}
