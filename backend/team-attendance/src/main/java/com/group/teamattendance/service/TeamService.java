package com.group.teamattendance.service;

import com.group.teamattendance.domain.Team;
import com.group.teamattendance.domain.TeamRepositoy;
import com.group.teamattendance.dto.TeamResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    // Bean 자동 연결
    private final TeamRepositoy teamRepositoy;

    TeamService(TeamRepositoy teamRepositoy) {
        this.teamRepositoy = teamRepositoy;
    }

    @Transactional
    public List<TeamResponse> searchTeam() {
        return teamRepositoy.findAll().stream().map(TeamResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void saveTeam(String name) {
        teamRepositoy.save(new Team(name));
    }

    @Transactional
    public void renameTeam(String teamName, String name) {
        Team team = teamRepositoy.findByName(teamName).orElseThrow(IllegalArgumentException::new);
        team.updateName(name);
        teamRepositoy.save(team);
    }
}
