package com.publisher_service.scheduler;

import com.kafka.Kafka;
import com.publisher_service.messaging.KafkaService;
import com.publisher_service.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@Slf4j
public class Scheduler {

    private KafkaService kafkaService;

    @Autowired
    public Scheduler(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    //@Scheduled(initialDelayString = "${initial.delay:1000}", fixedDelayString = "${fixed.delay:300000}")
    //@Scheduled(cron = "0 */5 * * * *")
    public void scheduledKafkaPublisher() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        ResponseDto responseDto = ResponseDto.builder()
                .traceId(UUID.randomUUID())
                .time(now)
                .id(10)
                .name("nitin")
                .employee("Employee")
                .build();
        log.info("Publishing data: {} to kafka topic: {} at: {}", responseDto, Kafka.Topics.SINGLE, now);
        kafkaService.publishEvent(Kafka.Topics.SINGLE, responseDto);
    }
}
