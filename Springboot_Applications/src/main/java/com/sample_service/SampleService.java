package com.sample_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication()
public class SampleService {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SampleService.class);
        System.getProperties().setProperty("spring.application.name", "com.sample_service.SampleService");
        //app.setAllowBeanDefinitionOverriding(true);
        app.run();
    }
}
