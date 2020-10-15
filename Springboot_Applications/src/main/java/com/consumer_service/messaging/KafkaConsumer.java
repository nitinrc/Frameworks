package com.consumer_service.messaging;

import com.kafka.Kafka;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topicPartitions = {@TopicPartition(topic = Kafka.Topics.SINGLE,
            partitions = {"0", "1", "2", "3", "4"})})
    public void processEventSingle(String response,
                             @Header(value = KafkaHeaders.OFFSET) List<Long> offsets,
                             @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received Kafka message: topic: {}, data: {}, offsets: {}",
                topic, response, offsets);
    }

    @KafkaListener(topicPartitions = {@TopicPartition(topic = Kafka.Topics.MULTIPLE,
            partitions = {"0", "1", "2", "3", "4"})})
    public void processEventMultiple(String response,
                                   @Header(value = KafkaHeaders.OFFSET) List<Long> offsets,
                                   @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received Kafka message: topic: {}, data: {}, offsets: {}",
                topic, response, offsets);
    }

//    @KafkaListener(topics = Kafka.Topics.SINGLE, groupId = "group_id")
}
