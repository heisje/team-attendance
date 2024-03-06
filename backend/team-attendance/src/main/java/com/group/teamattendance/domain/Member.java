package com.group.teamattendance.domain;

import com.group.teamattendance.common.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 생성
    long id;

    @ManyToOne
    @JoinColumn
    Team team;

    @Column(nullable = false)
    String name;

    @Column
    boolean role = false;

    @Column(nullable = true)
    LocalDate birthday;

    @Column(nullable = true)
    LocalDate workStartDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Attendance> attendances = new ArrayList<>();

    protected Member() {
    }

    public Member(Team team, String name, boolean role, LocalDate birthday, LocalDate workStartDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)입니다.", name));
        }
        if (team == null) {
            throw new IllegalArgumentException(String.format("잘못된 teamName(%s)입니다.", name));
        }
        this.team = team;
        this.name = name;
        this.role = role;
        this.birthday = birthday;
        this.workStartDate = workStartDate;
    }

    public boolean getRole() {
        return this.role;
    }

    public void updateRole(Role role) {
        if (role == Role.MEMBER) {
            this.role = false;
        }
        if (role == Role.MANAGER) {
            this.role = true;
        }
    }
}
