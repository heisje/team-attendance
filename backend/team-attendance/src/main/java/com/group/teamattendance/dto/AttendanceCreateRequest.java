package com.group.teamattendance.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AttendanceCreateRequest {
    Long memberId;
    LocalDateTime time;
    Boolean isGoToWork;
}
