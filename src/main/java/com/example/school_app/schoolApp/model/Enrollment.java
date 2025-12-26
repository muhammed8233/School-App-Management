package com.example.school_app.schoolApp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Override
    public String toString() {
        return "Enrollment{" +
                "course=" + course +
                ", id=" + id +
                ", student=" + student +
                '}';
    }
}
