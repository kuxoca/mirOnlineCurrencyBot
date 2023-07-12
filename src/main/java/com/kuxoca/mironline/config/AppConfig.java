package com.kuxoca.mironline.config;

import com.kuxoca.mironline.service.MainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Locale;

@Configuration
public class AppConfig {

    private final MainService mainService;

    public AppConfig(MainService mainService) {
        this.mainService = mainService;
    }

    @Bean("messageSource")
    public MessageSource messageSource() {

        ResourceBundleMessageSource messageSource =
                new ResourceBundleMessageSource();
        messageSource.setBasenames("messages/labels");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(new Locale("ru"));
        return messageSource;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        return scheduler;
    }

    @Scheduled(fixedDelay = 60 * 1000L, initialDelay = 0)
    public void scheduled1() {
        mainService.mainMethod();
    }
}
