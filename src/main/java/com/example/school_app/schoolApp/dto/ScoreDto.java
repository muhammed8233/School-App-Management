package com.example.school_app.schoolApp.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreDto {
    private Long studentId;
    private Long courseId;
    private Double examScore;
    private Double testScore;
    private Double assignmentScore;
    private Double finalScore;

    @Override
    public String toString() {
        return "ScoreDto{" +
                "assignmentScore=" + assignmentScore +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", examScore=" + examScore +
                ", testScore=" + testScore +
                ", finalScore=" + finalScore +
                '}';
    }
}
