package com.group.teamattendance.dto;

import lombok.Getter;

@Getter
public class TeamUpdateRequest {
    private String oldName;
    private String newName;
}
