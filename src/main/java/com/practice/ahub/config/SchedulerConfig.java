package com.practice.ahub.config;

import com.practice.ahub.service.FileModelService;
import com.practice.ahub.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class SchedulerConfig {

    private final MinioService minioService;
    private final FileModelService fileModelService;

    @Scheduled(cron = "0 35 11 * * *")
    public void scheduled() {
        fileModelService.fileGC();
    }

}
