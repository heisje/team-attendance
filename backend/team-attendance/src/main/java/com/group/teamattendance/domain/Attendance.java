package com.group.teamattendance.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 생성
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "attendance")
    private final List<AttendanceTime> attendanceTimes = new ArrayList<>();

    private Integer workingMinutes;

    private boolean dayOff;

    private boolean usingDayOff;

    public Attendance(Member member, LocalDate date) {
        this.member = member;
        this.date = date;
    }

    protected Attendance() {
    }

    public void addAttendanceTime(AttendanceTime attendanceTime) {
        attendanceTimes.add(attendanceTime);
        attendanceTime.setAttendance(this);
    }

    public void updateWorkingMinutes(Integer workingMinutes) {
        this.workingMinutes = workingMinutes;
    }

    public void updateDayOff(boolean isDayOff) {
        this.dayOff = isDayOff;
    }
}
