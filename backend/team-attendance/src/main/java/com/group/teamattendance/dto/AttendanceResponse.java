package com.group.teamattendance.dto;

import com.group.teamattendance.common.AttendanceDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class AttendanceResponse {

    List<AttendanceDetail> detail;
    Integer sum = 0;

    public AttendanceResponse(List<AttendanceDetail> detail, Integer sum) {
        this.detail = detail;
        this.sum = sum;
    }
}
