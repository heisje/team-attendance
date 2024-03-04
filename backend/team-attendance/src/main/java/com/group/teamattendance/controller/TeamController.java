package com.group.teamattendance.controller;

import com.group.teamattendance.dto.TeamCreateRequest;
import com.group.teamattendance.dto.TeamResponse;
import com.group.teamattendance.dto.TeamUpdateRequest;
import com.group.teamattendance.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamResponse> searchTeam() {
        return teamService.searchTeam();
    }

    @PostMapping
    public void saveTeam(@RequestBody TeamCreateRequest request) {
        teamService.saveTeam(request.getName());
    }

    @PutMapping
    public void renameTeam(@RequestBody TeamUpdateRequest request) {
        teamService.renameTeam(request.getOldName(), request.getNewName());
    }
}
