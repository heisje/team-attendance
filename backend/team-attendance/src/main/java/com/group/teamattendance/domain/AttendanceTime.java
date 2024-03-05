package com.group.teamattendance.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class AttendanceTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 생성
    private Long id;

    @ManyToOne
    @JoinColumn
    private Attendance attendance;

    private LocalDateTime time;

    private Boolean isGoToWork;

    public AttendanceTime(LocalDateTime time, boolean isGoToWork) {
        this.time = time;
        this.isGoToWork = isGoToWork;
    }

    protected AttendanceTime() {
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
