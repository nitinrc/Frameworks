package com.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.publisher_service.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class KafkaPublisher {
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected KafkaTemplate <String, String> kafkaTemplate;

    @Autowired
    public KafkaPublisher(KafkaTemplate <String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(String topic, ResponseDto responseDto) {
        ListenableFuture<SendResult<String, String>> future = null;
        try {
            objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
            objectMapper.registerModule(new Jdk8Module());
            objectMapper.registerModule(new JavaTimeModule());
            String responseAsString = objectMapper.writeValueAsString(responseDto);
            future = kafkaTemplate.send(topic, responseAsString);
            SendResult<String, String> sendResult = future.get(100, TimeUnit.SECONDS);
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            log.info("Published message to Kafka: topic: {}, data: {}, timestamp: {}, offset: {}",
                    topic, responseDto, recordMetadata.timestamp(), recordMetadata.offset());
        } catch (JsonProcessingException e) {
            log.error(String.valueOf(e));
        } catch (TimeoutException e) {
            future.cancel(false);
            log.error(String.valueOf(e));
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }
}
