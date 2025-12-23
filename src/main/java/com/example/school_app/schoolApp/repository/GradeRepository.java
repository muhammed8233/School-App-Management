package com.example.school_app.schoolApp.repository;

import com.example.school_app.schoolApp.Enum.Assessment;
import com.example.school_app.schoolApp.model.Enrollment;
import com.example.school_app.schoolApp.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    boolean existsByEnrollmentAndAssessmentType(Enrollment enrollment, Assessment assessmentType);

    List<Grade> findByEnrollmentId(Long enrollmentId);

    @Query("SELECT g.score FROM Grade g WHERE g.enrollment.id = :enrollmentId AND g.assessmentType = :assessment")
    Double findScore(@Param("enrollmentId") Long enrollmentId, @Param("assessment") Assessment assessment);

}


