package com.example.school_app.schoolApp.dto;

import com.example.school_app.schoolApp.model.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreDtoTest {
    ScoreDto scoreDto = new ScoreDto();

    @Test
    void testToVerifyStudentIdIsSet(){
        scoreDto.setStudentId(1L);
        assertEquals(1L, scoreDto.getStudentId());
    }

    @Test
    void testToVerifyCourseIdIsSet(){
        scoreDto.setCourseId(2L);
        assertEquals(2L, scoreDto.getCourseId());
    }


    @Test
    void verifyIfAssignmentScoreIsSet(){
        Grade grade = new Grade();
        grade.setScore(12.0);
        scoreDto.setAssignmentScore(grade.getScore());
        assertEquals(12.0, scoreDto.getAssignmentScore());
    }

    @Test
    void verifyTestScoreIsSet(){
        Grade grade = new Grade();
        grade.setScore(11);
        scoreDto.setTestScore(grade.getScore());
        assertEquals(11, scoreDto.getTestScore());
    }

    @Test
    void verifyExamScoreIsSet(){
        Grade grade = new Grade();
        grade.setScore(22.1);
        scoreDto.setExamScore(grade.getScore());
        assertEquals(22.1, scoreDto.getExamScore());
    }

}