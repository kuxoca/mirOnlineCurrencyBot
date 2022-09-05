package com.kuxoca.mironline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MironlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MironlineApplication.class, args);
    }
}
