package com.example.school_app.schoolApp.controller;

import com.example.school_app.schoolApp.dto.AttendanceRecordDto;
import com.example.school_app.schoolApp.services.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/attendance")
@Validated
public class AttendanceController {
    private final AttendanceRecordService attendanceRecordService;

    @Autowired
    public AttendanceController(AttendanceRecordService attendanceRecordService) {
        this.attendanceRecordService = attendanceRecordService;
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<AttendanceRecordDto> getStudentAttendance(@PathVariable Long studentId,
                                                                   @PathVariable Long courseId){
        AttendanceRecordDto  records = attendanceRecordService.getStudentAttendance(studentId, courseId);

        return ResponseEntity.ok(records);
    }

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceRecordDto recordDto) {
         attendanceRecordService.markStudentAttendance(recordDto.getStudentId(),
                 recordDto.getCourseId(), recordDto.getDate(), recordDto.getStatus());

         return ResponseEntity.ok("marked successfully");
    }

    @PostMapping("save")
    public ResponseEntity<List<AttendanceRecordDto>> saveAllAttendanceRecord(@RequestBody List<AttendanceRecordDto>
                                                                      attendanceRecordDtoList){
        List<AttendanceRecordDto> recordDtosv = attendanceRecordService.saveAllAttendanceRecords(attendanceRecordDtoList);

        return new ResponseEntity<>(recordDtosv, HttpStatus.CREATED);
    }

}
