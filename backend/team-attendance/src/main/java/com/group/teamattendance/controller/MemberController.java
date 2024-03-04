package com.group.teamattendance.controller;

import com.group.teamattendance.dto.MemberCreateRequest;
import com.group.teamattendance.dto.MemberResponse;
import com.group.teamattendance.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberResponse> searchMember() {
        return memberService.searchMember();
    }

    @PostMapping
    public void saveMember(@RequestBody MemberCreateRequest request) {
        memberService.saveMember(request);
    }


}
