package com.group.teamattendance.service;

import com.group.teamattendance.common.Role;
import com.group.teamattendance.domain.*;
import com.group.teamattendance.dto.MemberCreateRequest;
import com.group.teamattendance.dto.MemberOvertimeResponse;
import com.group.teamattendance.dto.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberService {

    // Bean 자동 연결
    private final TeamRepositoy teamRepositoy;
    private final MemberRepository memberRepository;
    private final MonthAttendanceRepository monthAttendanceRepository;
    private final AttendanceRepository attendanceRepository;

    public MemberService(TeamRepositoy teamRepositoy, MemberRepository memberRepository, MonthAttendanceRepository monthAttendanceRepository, AttendanceRepository attendanceRepository) {
        this.teamRepositoy = teamRepositoy;
        this.memberRepository = memberRepository;
        this.monthAttendanceRepository = monthAttendanceRepository;
        this.attendanceRepository = attendanceRepository;
    }


    @Transactional(readOnly = true)
    public List<MemberResponse> searchMember() {
        return memberRepository.findAll().stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void saveMember(MemberCreateRequest request) {
        Team team = teamRepositoy.findByName(request.getTeamName()).orElseThrow(IllegalArgumentException::new);

        Member member = new Member(team, request.getName(), request.getRole() == Role.MANAGER, request.getBirthday(), request.getWorkStartDate());
        if (request.getRole() == Role.MANAGER) {
            team.updateManager(member);
        }
        memberRepository.save(member);
    }

    /**
     * 특정 월에 대한 모든 직원의 초과 근무 시간을 조회합니다.
     * 각 직원의 ID, 이름, 그리고 해당 월에 대한 총 근무 시간(분 단위)이 포함된 리스트를 반환합니다.
     *
     * @param date 조회할 월의 어떤 날짜 (년과 월 정보만 사용됩니다)
     * @return 각 직원의 초과 근무 정보를 담은 MemberOvertimeResponse 객체의 리스트
     */
    @Transactional
    public List<MemberOvertimeResponse> searchOvertimeMembers(LocalDate date) {
        LocalDate firstDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        // 특정 기간에 대한 모든 출석 레코드를 가져옵니다.
        List<Attendance> allAttendances = attendanceRepository.findAllByBetweenMonth(firstDayOfMonth, lastDayOfMonth);

        // 출석 레코드를 멤버 ID별로 그룹화합니다.
        Map<Long, List<Attendance>> attendanceMap = allAttendances.stream()
                .collect(Collectors.groupingBy(attendance -> attendance.getMember().getId()));

        // 모든 멤버를 가져옵니다.
        List<Member> allMembers = memberRepository.findAll();

        // 각 멤버별로 총 근무 시간을 계산하여 결과 리스트를 생성합니다.
        return allMembers.stream()
                .map(member -> {
                    List<Attendance> attendances = attendanceMap.getOrDefault(member.getId(), Collections.emptyList());
                    int totalWorkingMinutes = attendances.stream()
                            .mapToInt(Attendance::getWorkingMinutes)
                            .sum();
                    return new MemberOvertimeResponse(member.getId(), member.getName(), totalWorkingMinutes);
                })
                .toList();


    }
}
