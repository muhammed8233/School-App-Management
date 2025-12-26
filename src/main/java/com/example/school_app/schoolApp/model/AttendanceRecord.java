package com.example.school_app.schoolApp.model;

import com.example.school_app.schoolApp.Enum.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Status status;

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
