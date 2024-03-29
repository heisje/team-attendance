package com.group.teamattendance.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId AND a.date = :date")
    Optional<Attendance> findByMemberIdAndDate(@Param("memberId") Long memberId, @Param("date") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByMemberIdAndMonthBetween(@Param("memberId") Long memberId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    @Query("select a from Attendance a where a.member.id = :memberId")
    List<Attendance> findByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query("update Attendance a set a.usingDayOff = true where a.date <= :date and a.dayOff = true")
    void updateUsingDayOffForDate(@Param("date") LocalDate date);

    @Query("SELECT a from Attendance a where :firstDayOfMonth <= a.date and a.date <= :lastDayOfMonth")
    List<Attendance> findAllByBetweenMonth(@Param("firstDayOfMonth") LocalDate firstDayOfMonth, @Param("lastDayOfMonth") LocalDate lastDayOfMonth);
}
