package com.publisher_service.publisher.service;

import com.kafka.Kafka;
import com.publisher_service.messaging.KafkaService;
import com.publisher_service.model.Response;
import com.publisher_service.model.ResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@NoArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

    private Response response;
    private KafkaService kafkaService;

    @Autowired
    public PublisherServiceImpl(Response response, KafkaService kafkaService) {
        this.response = response;
        this.kafkaService = kafkaService;
    }

    public ResponseDto getResponseWithName(String name) {
        Integer id = 1000; //Should ideally come from dao
        ResponseDto responseDto = response.toDto(id, name);
        responseDto.setTraceId(UUID.randomUUID());
        kafkaService.publishEvent(Kafka.Topics.SINGLE, responseDto);
        return responseDto;
    }

    public ResponseDto getResponseWithIdAndName(Integer id, String name) {
        ResponseDto responseDto = response.toDto(id, name);
        responseDto.setTraceId(UUID.randomUUID());
        kafkaService.publishEvent(Kafka.Topics.MULTIPLE, responseDto);
        return responseDto;
    }
}
