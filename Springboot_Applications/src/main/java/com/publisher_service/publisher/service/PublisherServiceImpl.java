package com.publisher_service.publisher.service;

import com.publisher_service.messaging.KafkaPublisher;
import com.publisher_service.model.Response;
import com.publisher_service.model.ResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

    private Response response;
    private KafkaPublisher kafkaPublisher;

    @Autowired
    public PublisherServiceImpl(Response response, KafkaPublisher kafkaPublisher) {
        this.response = response;
        this.kafkaPublisher = kafkaPublisher;
    }

    public ResponseDto getResponseWithName(String name) {
        Integer id = 1000; //Should ideally come from dao
        ResponseDto responseDto = response.toDto(id, name);
        kafkaPublisher.publishEvent("kafka-test", responseDto);
        return responseDto;
    }

    public ResponseDto getResponseWithIdAndName(Integer id, String name) {
        ResponseDto responseDto = response.toDto(id, name);
        kafkaPublisher.publishEvent("kafka-test", responseDto);
        return responseDto;
    }
}
