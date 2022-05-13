package com.kuxoca.mironline;

import com.kuxoca.mironline.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class MironlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MironlineApplication.class, args);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }

    @Autowired
    MainService mainService;

    @Scheduled(fixedDelay = 1 * 60 * 1000L, initialDelay = 0)
    public void scheduled1() throws IOException {
        try {
            mainService.mainMethod();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
