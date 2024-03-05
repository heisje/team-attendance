package com.group.teamattendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TeamAttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamAttendanceApplication.class, args);
    }

}
