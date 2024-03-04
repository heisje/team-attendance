package com.group.teamattendance.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// Team의 id를 Long으로 만든다.
public interface TeamRepositoy extends JpaRepository<Team, Long> {

    Optional<Team> findByName(@Param("name") String teamName);
}
