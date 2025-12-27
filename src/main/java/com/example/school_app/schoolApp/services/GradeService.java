package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Assessment;
import com.example.school_app.schoolApp.dto.GradeDto;
import com.example.school_app.schoolApp.dto.ScoreDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface GradeService {
    GradeDto recordStudentScore(Long studentId, Long courseId, Assessment type, double score);

    double computeFinalScore(Long enrollmentId);

    List<GradeDto> saveAllGrades(List<GradeDto> gradeRequests);

    ScoreDto getStudentScoreInCourse(Long studentId, Long courseId);
}
