package com.calenderdeepening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CalenderDeepeningApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalenderDeepeningApplication.class, args);
    }

}
