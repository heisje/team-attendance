package com.group.teamattendance.dto;

import com.group.teamattendance.domain.Team;
import lombok.Getter;

@Getter
public class TeamResponse {
    private final String name;
//    private final String manager;     // 매니저 이름
//    private final int memberCount;

    public TeamResponse(Team team) { //, String manager, int memberCount
        this.name = team.getName();
//        this.manager = manager;
//        this.memberCount = memberCount;
    }

}
