package com.group.teamattendance.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceTimeRepository extends JpaRepository<AttendanceTime, Long> {
}
