package com.group.teamattendance.dto;

import lombok.Getter;

@Getter
public class MemberOvertimeResponse {
    long id;
    String name;
    Integer overtimeMinutes;

    public MemberOvertimeResponse(long memberId, String name, Integer overtimeMinutes) {
        this.id = memberId;
        this.name = name;
        this.overtimeMinutes = overtimeMinutes;
    }
}
