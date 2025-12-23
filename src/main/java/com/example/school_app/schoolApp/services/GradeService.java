package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.Enum.Assessment;
import com.example.school_app.schoolApp.exception.GradeNotFoundException;
import com.example.school_app.schoolApp.exception.ScoreAlreadyExistException;
import com.example.school_app.schoolApp.model.Course;
import com.example.school_app.schoolApp.model.Enrollment;
import com.example.school_app.schoolApp.model.Grade;
import com.example.school_app.schoolApp.model.Student;
import com.example.school_app.schoolApp.repository.GradeRepository;
import com.example.school_app.schoolApp.dto.GradeDto;
import com.example.school_app.schoolApp.dto.ScoreDto;
import com.example.school_app.schoolApp.dto.EnrollmentDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeService implements GradeServiceInterface {

    @Autowired private final GradeRepository gradeRepository;
    @Autowired private final StudentService studentService;
    @Autowired private final CourseService courseService;
    @Autowired private final EnrollmentService enrollmentService;

    public GradeService(StudentService studentService,
                        CourseService courseService,
                        GradeRepository gradeRepository,
                        EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeRepository = gradeRepository;
        this.enrollmentService = enrollmentService;
    }

    @Override
    public GradeDto recordStudentScore(Long studentId, Long courseId, Assessment type, double score) {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);

        Enrollment enrollment = enrollmentService.findEnrollmentByStudentAndCourse(student, course);

        boolean existingEnrollment = gradeRepository.
                existsByEnrollmentAndAssessmentType(enrollment, type);
        if (existingEnrollment){
            throw new ScoreAlreadyExistException(" the student score has been recorded for this assessment type");
        }

        Grade grade = new Grade();
        grade.setEnrollment(enrollment);
        grade.setAssessmentType(type);
        grade.setScore(score);
        Grade savedGrade = gradeRepository.save(grade);

        GradeDto gradeDto = new GradeDto();
        gradeDto.setStudentId(savedGrade.getId());
        gradeDto.setStudentId(savedGrade.getEnrollment().getStudent().getId());
        gradeDto.setCourseId(savedGrade.getEnrollment().getCourse().getId());
        gradeDto.setScore(savedGrade.getScore());
        gradeDto.setAssessmentType(savedGrade.getAssessmentType());

        return gradeDto;
    }
    @Override
    public List<ScoreDto> getAllStudentScoreInACourse() {
        List<EnrollmentDto> enrollments = enrollmentService.getAllEnrollment();
        List<ScoreDto> results = new ArrayList<>();

        for (EnrollmentDto enrollment : enrollments) {
            ScoreDto dto = new ScoreDto();
            dto.setStudentId(enrollment.getStudentId());
            dto.setCourseId(enrollment.getCourseId());

            Double testVal = gradeRepository.findScore(enrollment.getEnrollmentId(), Assessment.TEST);
            Double assignmentVal = gradeRepository.findScore(enrollment.getEnrollmentId(), Assessment.ASSIGNMENT);
            Double examVal = gradeRepository.findScore(enrollment.getEnrollmentId(), Assessment.EXAM);

            dto.setTestScore(testVal);
            dto.setAssignmentScore(assignmentVal);
            dto.setExamScore(examVal);

            double finalScore = computeFinalScore(enrollment.getEnrollmentId());
            dto.setFinalScore(finalScore);

            results.add(dto);
        }
        return results;
    }

    @Override
    public double computeFinalScore(Long enrolmentId){
        List<Grade> grades = gradeRepository.findByEnrollmentId(enrolmentId);
        if(grades == null || grades.isEmpty()){
            throw  new GradeNotFoundException("grade not found");
        }
        double testScore = 0;
        double examScore = 0;

        for(Grade grade : grades){
            System.out.println("Grade Type: " + grade.getAssessmentType() + " Score: " + grade.getScore());
            if(grade.getAssessmentType() == Assessment.TEST){
                testScore = grade.getScore();
            }else if(grade.getAssessmentType() == Assessment.EXAM){
                examScore = grade.getScore();
            }
        }

        double result = 0.4 * testScore + 0.6 * examScore;

        return result;
    }

    @Transactional
    @Override
    public List<GradeDto> saveAllGrades(List<GradeDto> gradeRequests) {
        if (gradeRequests == null || gradeRequests.isEmpty()) {
            throw new GradeNotFoundException("Grade list cannot be empty.");
        }

        List<GradeDto> results = new ArrayList<>();

        for (GradeDto request : gradeRequests) {

            GradeDto savedDto = recordStudentScore(request.getStudentId(),
                    request.getCourseId(), request.getAssessmentType(),
                    request.getScore());

            results.add(savedDto);
        }
        return results;
    }

}
