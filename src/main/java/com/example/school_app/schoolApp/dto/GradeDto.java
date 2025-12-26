package com.example.school_app.schoolApp.dto;

import com.example.school_app.schoolApp.Enum.Assessment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeDto {
   private Long studentId;
   private Long courseId;
   private double score;
   private Assessment assessmentType;

    @Override
    public String toString() {
        return "GradeDto{" +
                "assessmentType=" + assessmentType +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", score=" + score +
                '}';
    }
}
