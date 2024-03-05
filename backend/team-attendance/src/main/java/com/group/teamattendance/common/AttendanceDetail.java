package com.group.teamattendance.common;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AttendanceDetail {
    private final LocalDate date;
    private final Integer workingMinutes;
    private final boolean isDayOff;
    private final boolean usingDayOff;

    public AttendanceDetail(LocalDate date, Integer workingMinutes, boolean isDayOff, boolean usingDayOff) {
        this.date = date;
        this.workingMinutes = workingMinutes;
        this.isDayOff = isDayOff;
        this.usingDayOff = usingDayOff;
    }
}
