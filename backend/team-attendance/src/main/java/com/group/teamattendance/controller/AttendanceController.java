package com.group.teamattendance.controller;


import com.group.teamattendance.dto.AttendanceCreateRequest;
import com.group.teamattendance.dto.AttendanceResponse;
import com.group.teamattendance.dto.DayOffUpdateRequest;
import com.group.teamattendance.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/attendance")
public class AttendanceController {

    AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public AttendanceResponse searchAttendance(@RequestParam Long memberId, @RequestParam LocalDate date) {
        return attendanceService.searchAttendance(memberId, date);
    }

    @PostMapping
    public void createAttendance(@RequestBody AttendanceCreateRequest attendanceCreateRequest) {
        attendanceService.createAttendance(attendanceCreateRequest.getMemberId(), attendanceCreateRequest.getTime(), attendanceCreateRequest.getIsGoToWork());
    }

    @PutMapping("/day-off")
    public void updateDayOff(@RequestBody DayOffUpdateRequest dayOffUpdateRequest) {
        attendanceService.updateDayOff(dayOffUpdateRequest.getMemberId(), dayOffUpdateRequest.getDate(), dayOffUpdateRequest.getIsDayOff());
    }
}
