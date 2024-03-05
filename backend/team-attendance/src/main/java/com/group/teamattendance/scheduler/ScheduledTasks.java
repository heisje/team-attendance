package com.group.teamattendance.scheduler;

import com.group.teamattendance.service.AttendanceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduledTasks {

    private final AttendanceService attendanceService;

    public ScheduledTasks(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Scheduled(cron = "0 14 0 * * ?") // 매일 자정에 실행
    public void updateUsingDayOff() {
        LocalDate today = LocalDate.now();
        attendanceService.updateUsingDayOff(today);
    }
}
