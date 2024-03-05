package com.group.teamattendance.dto;

import lombok.Getter;

@Getter
public class TeamResponse {
    private final String name;
    private final String manager;     // 매니저 이름
//    private final int memberCount;

    public TeamResponse(String teamName, String managerName) { //, String manager, int memberCount
        this.name = teamName;
        this.manager = managerName;
//        this.memberCount = memberCount;
    }

}
