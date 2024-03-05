package com.group.teamattendance.service;

import com.group.teamattendance.common.AttendanceDetail;
import com.group.teamattendance.domain.*;
import com.group.teamattendance.dto.AttendanceResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    MemberRepository memberRepository;
    AttendanceRepository attendanceRepository;
    AttendanceTimeRepository attendanceTimeRepository;

    public AttendanceService(MemberRepository memberRepository, AttendanceRepository attendanceRepository, AttendanceTimeRepository attendanceTimeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.memberRepository = memberRepository;
        this.attendanceTimeRepository = attendanceTimeRepository;
    }

    @Transactional(readOnly = true)
    public AttendanceResponse searchAttendance(Long memberId, LocalDate date) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        // 기존에 날짜/맴버 기반 출결 검색
        LocalDate firstDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        final Integer[] sum = {0};
        List<AttendanceDetail> mapAttendances = attendanceRepository.findByMemberIdAndMonthBetween(memberId, firstDayOfMonth, lastDayOfMonth).stream()
                .map(at -> {
                    AttendanceDetail attendanceDetail = new AttendanceDetail(at.getDate(), at.getWorkingMinutes(), at.isDayOff(), at.getUsingDayOff());
                    sum[0] += at.getWorkingMinutes();
                    return attendanceDetail;
                })
                .collect(Collectors.toList());

        return new AttendanceResponse(mapAttendances, sum[0]);
    }

    @Transactional
    public void createAttendance(Long memberId, LocalDateTime time, Boolean isGoToWork) {
        // 받은 request를 시간 객체로
        AttendanceTime attendanceTime = new AttendanceTime(time, isGoToWork);

        // 맴버 체크
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        if (time == null) {
            throw new IllegalArgumentException();
        }

        // 기존에 날짜/맴버 기반 출결 검색
        Attendance attendance = attendanceRepository.findByMemberIdAndDate(memberId, time.toLocalDate()).orElse(new Attendance(member, time.toLocalDate()));

        // 객체 연결
        attendance.addAttendanceTime(attendanceTime);
        // attendanceTime.setAttendance(attendance);

        // 시간 계산
        List<AttendanceTime> sortedAttendanceTimeList = attendance.getAttendanceTimes().stream()
                .sorted(Comparator.comparing(AttendanceTime::getTime))
                .toList();
        LocalTime stack = LocalTime.of(0, 0, 0);
        Duration workingMinutes = Duration.ofHours(0);

        for (AttendanceTime at : sortedAttendanceTimeList) {
            if (at.getIsGoToWork()) {
                stack = at.getTime().toLocalTime();
            } else {
                Duration duration = Duration.between(stack, at.getTime().toLocalTime());
                workingMinutes = workingMinutes.plus(duration);  // 차이를 sum에 더하기
            }
        }

        attendance.updateWorkingMinutes((int) workingMinutes.toMinutes());
        attendanceRepository.save(attendance);
        attendanceTimeRepository.save(attendanceTime);
    }

    @Transactional
    public void updateDayOff(long memberId, LocalDate date, boolean isDayOff) {
        // 기존에 날짜/맴버 기반 출결 검색
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        int daysBetween = Period.between(LocalDate.now(), date).getDays();
        // System.out.println(Period.between(LocalDate.now(), date).getDays());
        if (daysBetween < member.getTeam().getDayOffAllowedDuration()) {
            // TODO: 팀의 연차적용기간이 짧다는 경고문
            throw new IllegalArgumentException();
        }
        Attendance attendance = attendanceRepository.findByMemberIdAndDate(memberId, date).orElse(new Attendance(member, date));

        attendance.updateDayOff(isDayOff);
        attendanceRepository.save(attendance);
    }

    @Transactional
    public void updateUsingDayOff(LocalDate today) {
        attendanceRepository.updateUsingDayOffForDate(today);
    }


}
