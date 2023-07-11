package com.kuxoca.mironline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MirOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MirOnlineApplication.class, args);
    }
}
