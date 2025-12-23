package com.example.school_app.schoolApp.repository;

import com.example.school_app.schoolApp.Enum.Status;
import com.example.school_app.schoolApp.model.AttendanceRecord;
import com.example.school_app.schoolApp.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    boolean existsByEnrollmentAndDate(Enrollment enrollment, LocalDate today);

    Long countByEnrollmentStudentIdAndEnrollmentCourseIdAndStatus(Long studentId, Long courseId, Status status);

    AttendanceRecord findTopByEnrollmentStudentIdAndEnrollmentCourseIdOrderByDateDesc(Long studentId, Long courseId);
}

