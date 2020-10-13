package com.publisher_service.publisher.service;

import com.publisher_service.messaging.KafkaService;
import com.publisher_service.model.Response;
import com.publisher_service.model.ResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        kafkaService.publishEvent("kafka-single", responseDto);
        return responseDto;
    }

    public ResponseDto getResponseWithIdAndName(Integer id, String name) {
        ResponseDto responseDto = response.toDto(id, name);
        kafkaService.publishEvent("kafka-multiple", responseDto);
        return responseDto;
    }
}
