package com.consumer_service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-single", groupId = "group_id")
    public void processEventSingle(String response) {
        log.info("Received Kafka message: {}", response);
    }

    @KafkaListener(topics = "kafka-multiple", groupId = "group_id")
    public void processEventMultiple(String response) {
        log.info("Received Kafka message: {}", response);
    }

//    @KafkaListener(topicPartitions = {@TopicPartition(topic = "kafka-single")})
//    public void processEventSingle(String response,
//                                   Acknowledgment ack,
//                                   @Header(value = KafkaHeaders.OFFSET) List<Long> offsets,
//                                   @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic) {
//    public void processEventSingle(String response,
//                             Acknowledgment ack,
//                             @Header(value = KafkaHeaders.OFFSET) List<Long> offsets,
//                             @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic) {
//        log.info("Received Kafka message: topic: {}, data: {}, ack: {}, offsets: {}",
//                topic, response, ack, offsets);
//    }
}
