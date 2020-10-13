package com.publisher_service.messaging;

import com.kafka.KafkaPublisher;
import com.publisher_service.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaService {

    private KafkaPublisher kafkaPublisher;

    @Autowired
    public KafkaService(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    @Retryable(include = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void publishEvent(String topic, ResponseDto responseDto) {
        kafkaPublisher.publishEvent(topic, responseDto);
    }
}
