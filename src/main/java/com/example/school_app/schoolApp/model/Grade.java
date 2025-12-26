package com.example.school_app.schoolApp.model;

import com.example.school_app.schoolApp.Enum.Assessment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
