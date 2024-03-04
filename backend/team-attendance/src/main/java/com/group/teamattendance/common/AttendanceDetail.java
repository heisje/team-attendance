package com.group.teamattendance.common;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AttendanceDetail {
    private final LocalDate date;
    private final Integer workingMinutes;
    // private final boolean usingDayOff;

    public AttendanceDetail(LocalDate date, Integer workingMinutes) {
        this.date = date;
        this.workingMinutes = workingMinutes;
        // this.usingDayOff = usingDayOff;
    }
}
