package com.group.teamattendance.dto;

import com.group.teamattendance.common.Role;
import com.group.teamattendance.domain.Member;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberResponse {
    private final String name;
    private final String team;
    private final Role role;
    private final LocalDate birthday;
    private final LocalDate workStartDate;

    public MemberResponse(Member member) {
        this.name = member.getName();
        this.team = member.getTeam().getName();
        this.role = member.getRole() ? Role.MANAGER : Role.MEMBER;
        this.birthday = member.getBirthday();
        this.workStartDate = member.getWorkStartDate();
    }
}
