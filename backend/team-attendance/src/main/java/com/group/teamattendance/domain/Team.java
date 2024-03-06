package com.group.teamattendance.domain;

import com.group.teamattendance.common.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 생성
    private final Long id = null;

    @OneToMany(mappedBy = "team")
    private final List<Member> member = new ArrayList<>();

    @Column(nullable = false, length = 255)     // length 255는 생략가능 (기본)
    private String name;

    private final Integer dayOffAllowedDuration = 1;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Member manager;

    protected Team() {
    }

    public Team(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)입니다.", name));
        }
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateManager(Member manager) {
        if (this.manager != null) {
            this.manager.updateRole(Role.MEMBER);
        }
        this.manager = manager;
        manager.updateRole(Role.MANAGER);
    }
}
