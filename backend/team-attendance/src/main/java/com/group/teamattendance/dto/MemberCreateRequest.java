package com.group.teamattendance.dto;

import com.group.teamattendance.common.Role;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberCreateRequest {
    private String name;
    private String teamName;
    private Role role;
    private LocalDate birthday;
    private LocalDate workStartDate;

}
