package com.kafka;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Kafka {
    public static final class Topics {
        public static final String SINGLE = "single";
        public static final String MULTIPLE = "multiple";
    }
}
