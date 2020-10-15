package com.publisher_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages = {
        "com.publisher_service",
        "com.kafka"
})
public class PublisherService {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PublisherService.class);
        System.getProperties().setProperty("spring.application.name", "com.publisher_service.PublisherService");
        app.run();
    }
}
