package com.group.teamattendance.service;

import com.group.teamattendance.common.Role;
import com.group.teamattendance.domain.Member;
import com.group.teamattendance.domain.MemberRepository;
import com.group.teamattendance.domain.Team;
import com.group.teamattendance.domain.TeamRepositoy;
import com.group.teamattendance.dto.MemberCreateRequest;
import com.group.teamattendance.dto.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    // Bean 자동 연결
    private final TeamRepositoy teamRepositoy;
    private final MemberRepository memberRepository;

    MemberService(MemberRepository memberRepository, TeamRepositoy teamRepositoy) {
        this.memberRepository = memberRepository;
        this.teamRepositoy = teamRepositoy;
    }

    public List<MemberResponse> searchMember() {
        return memberRepository.findAll().stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void saveMember(MemberCreateRequest request) {
        Team team = teamRepositoy.findByName(request.getTeamName()).orElseThrow(IllegalArgumentException::new);

        Member member = new Member(team, request.getName(), request.getRole() == Role.MANAGER, request.getBirthday(), request.getWorkStartDate());
        memberRepository.save(member);
    }


}
