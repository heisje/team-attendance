package com.group.teamattendance.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class MonthAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 생성
    long id;

    private Integer monthWorkingMinutes;
    private LocalDate month;
    private long memberId;

    public MonthAttendance(long memberId, LocalDate month, Integer monthWorkingMinutes) {
        this.monthWorkingMinutes = monthWorkingMinutes;
        this.month = month;
        this.memberId = memberId;
    }

    protected MonthAttendance() {
    }
}
