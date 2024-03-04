package com.group.teamattendance.dto;


import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DayOffUpdateRequest {
    private long memberId;
    private LocalDate date;
    private Boolean isDayOff;
}
