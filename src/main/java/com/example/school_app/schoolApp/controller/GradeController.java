package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.GradeDto;
import com.example.school_app.schoolApp.dto.ScoreDto;
import com.example.school_app.schoolApp.dto.StudentDto;
import com.example.school_app.schoolApp.services.GradeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/grade")
@Validated
public class GradeController {

    @Autowired private final GradeServiceInterface gradeServiceInterface;

    public GradeController(GradeServiceInterface gradeServiceInterface){
        this.gradeServiceInterface = gradeServiceInterface;
    }

    @PostMapping("score")
    public ResponseEntity<GradeDto> recordStudentScore(@RequestBody GradeDto gradeDto){
       GradeDto save = gradeServiceInterface.recordStudentScore(gradeDto.getStudentId(),
                gradeDto.getCourseId(), gradeDto.getAssessmentType(), gradeDto.getScore());
         return new ResponseEntity<>(save, HttpStatus.CREATED);
    }


    @GetMapping("all-scores")
    public ResponseEntity<List<ScoreDto>> getAllStudentScoreInACourse(){
        List<ScoreDto> records = gradeServiceInterface.getAllStudentScoreInACourse();

        return  ResponseEntity.ok(records);
    }


    @GetMapping("/{enrollmentId}/final-score")
    public ResponseEntity<Double> getFinalScore(@PathVariable Long enrollmentId){
        Double  computeFinalScore = gradeServiceInterface.computeFinalScore(enrollmentId);

        return ResponseEntity.ok(computeFinalScore);
    }
}
