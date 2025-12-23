package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Assessment;
import com.example.school_app.schoolApp.dto.GradeDto;
import com.example.school_app.schoolApp.dto.ScoreDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface GradeServiceInterface  {
    GradeDto recordStudentScore(Long studentId, Long courseId, Assessment type, double score);

   List<ScoreDto> getAllStudentScoreInACourse();

    double computeFinalScore(Long enrollmentId);

    @Transactional
    List<GradeDto> saveAllGrades(List<GradeDto> gradeRequests);
}
