package com.consumer_service;

import com.publisher_service.PublisherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableCaching
@SpringBootApplication(scanBasePackages = {
        "com.consumer_service",
        "com.kafka"
})
public class ConsumerService {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PublisherService.class);
        System.getProperties().setProperty("spring.application.name", "com.consumer_service.ConsumerService");
        app.run();
    }
}
