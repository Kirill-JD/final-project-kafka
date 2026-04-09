package ru.ycan.analytics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.ycan.analytics.service.service.AnalyticsService;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class Application implements CommandLineRunner {

    private final AnalyticsService analyticsService;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        analyticsService.analyticsProcess();
    }
}